package com.kuro.musicplayer.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static final String BASEPATH = "https://api.imjad.cn/";

    public static Retrofit getBaseRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEPATH).build();
        return retrofit;
    }

    public static Retrofit getGsonRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASEPATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
