package com.kuro.musicplayer.utils;

import com.google.gson.Gson;
import com.kuro.musicplayer.model.OnlineMusicBean;
import com.kuro.musicplayer.model.OnlineMusicDownloadBean;
import com.kuro.musicplayer.model.OnlineMusicPlaylistDetail;
import com.kuro.musicplayer.model.OnlineMusicsDetail;
import com.kuro.musicplayer.model.OnlineSongSheetBean;
import com.kuro.musicplayer.service.web.MusicServiceI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class WebRequestUtil {

    //MusicList: https://api.imjad.cn/cloudmusic/?type=playlist&id=2392116731

    private static MusicServiceI getServiceThird() {
        return RetrofitFactory.getGsonRetrofitThird().create(MusicServiceI.class);
    }

    private static MusicServiceI getServiceOfficial() {
        return RetrofitFactory.getGsonRetrofitOffical().create(MusicServiceI.class);
    }

    public static String parseStringToGson(String jsonStr) {
        return new Gson().toJson(jsonStr);
    }

    public static OnlineSongSheetBean getSongSheetThird() {
        Call<OnlineSongSheetBean> call = getServiceThird().getMusicSheet("playlist", 2392116731L);
        OnlineSongSheetBean bean = new OnlineSongSheetBean();
        try {
            bean = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static List<OnlineMusicBean> getOnlineMusicsBySongSheetThird(OnlineSongSheetBean bean) {
        List<OnlineMusicBean> result = new ArrayList<>();
        Call<OnlineMusicBean> call;
        int i =0;
        for (OnlineSongSheetBean.PlaylistBean.TrackIdsBean playList : bean.getPlaylist().getTrackIds()) {
            if (i == 15) {
                break;
            }
            call = getServiceThird().getMusicDetails("detail", playList.getId());
            try {
                result.add(call.execute().body());;
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
        return result;
    }

    public static List<OnlineMusicDownloadBean> getOnlineSongThird(OnlineSongSheetBean bean) {
        List<OnlineMusicDownloadBean> result = new ArrayList<OnlineMusicDownloadBean>();
        Call<OnlineMusicDownloadBean> call;
        int i = 0;
        for (OnlineSongSheetBean.PlaylistBean.TrackIdsBean playList : bean.getPlaylist().getTrackIds()) {
            if (i == 15) {
                break;
            }
            call = getServiceThird().getMusics("song", playList.getId());
            try {
                result.add(call.execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
        return result;
    }

    public static List<OnlineMusicDownloadBean> getOnlineSongByIds(int[] ids) {
        List<OnlineMusicDownloadBean> result = new ArrayList<OnlineMusicDownloadBean>();
        Call<OnlineMusicDownloadBean> call;
        for (int id : ids) {
            call = getServiceThird().getMusics("song", id);
            try {
                result.add(call.execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static OnlineMusicPlaylistDetail getPlaylistDetailOfficial() {
        //http://music.163.com/api/playlist/detail/?id=2392116731
        Call<OnlineMusicPlaylistDetail> call =
                getServiceOfficial().getPlaylistDetailOfficial( 2392116731L);
        OnlineMusicPlaylistDetail detail;
        try {
            detail = call.execute().body();
            return detail;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OnlineMusicsDetail getMusicsDetail(int[] ids) {
        Call<OnlineMusicsDetail> call = getServiceOfficial().getMusicstDetailOfficial(ids);
        OnlineMusicsDetail detail;
        try {
            detail = call.execute().body();
            return detail;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
