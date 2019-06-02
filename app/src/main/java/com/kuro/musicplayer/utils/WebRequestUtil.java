package com.kuro.musicplayer.utils;

import com.google.gson.Gson;
import com.kuro.musicplayer.model.OnlineMusicBean;
import com.kuro.musicplayer.model.OnlineMusicDownloadBean;
import com.kuro.musicplayer.model.OnlineSongSheetBean;
import com.kuro.musicplayer.service.web.MusicServiceI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebRequestUtil {

    //MusicList: https://api.imjad.cn/cloudmusic/?type=playlist&id=2392116731

    private static MusicServiceI getService() {
        return RetrofitFactory.getGsonRetrofit().create(MusicServiceI.class);
    }

    public static String parseStringToGson(String jsonStr) {
        return new Gson().toJson(jsonStr);
    }

    public static OnlineSongSheetBean getSongSheet() {
        Call<OnlineSongSheetBean> call = getService().getMusicSheet("playlist", 2392116731L);
        OnlineSongSheetBean bean = new OnlineSongSheetBean();
        try {
            bean = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static List<OnlineMusicBean> getOnlineMusicsBySongSheet(OnlineSongSheetBean bean) {
        List<OnlineMusicBean> result = new ArrayList<>();
        Call<OnlineMusicBean> call;
        int i =0;
        for (OnlineSongSheetBean.PlaylistBean.TrackIdsBean playList : bean.getPlaylist().getTrackIds()) {
            if (i == 15) {
                break;
            }
            call = getService().getMusicDetails("detail", playList.getId());
            try {
                result.add(call.execute().body());;
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
        return result;
    }

    public static List<OnlineMusicDownloadBean> getOnlineSong(OnlineSongSheetBean bean) {
        List<OnlineMusicDownloadBean> result = new ArrayList<OnlineMusicDownloadBean>();
        Call<OnlineMusicDownloadBean> call;
        int i = 0;
        for (OnlineSongSheetBean.PlaylistBean.TrackIdsBean playList : bean.getPlaylist().getTrackIds()) {
            if (i == 15) {
                break;
            }
            call = getService().getMusics("song", playList.getId());
            try {
                result.add(call.execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
        return result;
    }
}
