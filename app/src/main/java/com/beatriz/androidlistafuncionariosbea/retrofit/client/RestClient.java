package com.beatriz.androidlistafuncionariosbea.retrofit.client;

import com.beatriz.androidlistafuncionariosbea.retrofit.service.FuncionarioService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestClient {

    public final Retrofit getRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FuncionarioService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}
