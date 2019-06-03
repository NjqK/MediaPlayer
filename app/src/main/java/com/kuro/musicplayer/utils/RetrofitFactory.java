package com.kuro.musicplayer.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static final String BASEPATH_THIRD = "https://api.imjad.cn/";
    private static final String BASEPATH_OFFICIAL = "http://music.163.com/api/";

    public static Retrofit getBaseRetrofitThird() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEPATH_THIRD).build();
        return retrofit;
    }

    public static Retrofit getGsonRetrofitThird() {
        return new Retrofit.Builder()
                .baseUrl(BASEPATH_THIRD)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getGsonRetrofitOffical() {
        return new Retrofit.Builder()
                .baseUrl(BASEPATH_OFFICIAL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
