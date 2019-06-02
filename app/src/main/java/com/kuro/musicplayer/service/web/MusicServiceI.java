package com.kuro.musicplayer.service.web;

import com.kuro.musicplayer.model.OnlineMusicBean;
import com.kuro.musicplayer.model.OnlineMusicDownloadBean;
import com.kuro.musicplayer.model.OnlineMusicRequest;
import com.kuro.musicplayer.model.OnlineSongSheetBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MusicServiceI {

    @GET("cloudmusic/")
    Call<OnlineMusicBean> getMusicDetails(@Query("type") String type, @Query("id") int id);

    @GET("cloudmusic/")
    Call<OnlineSongSheetBean> getMusicSheet(@Query("type") String type, @Query("id") long id);

    //https://api.imjad.cn/cloudmusic/?type=song&id=38019248&br=128000
    @GET("cloudmusic/")
    Call<OnlineMusicDownloadBean> getMusics(@Query("type") String type, @Query("id") long id);

}
