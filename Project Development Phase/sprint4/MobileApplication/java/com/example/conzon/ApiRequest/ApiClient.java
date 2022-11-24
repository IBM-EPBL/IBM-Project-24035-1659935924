package com.example.conzon.ApiRequest;

import com.example.conzon.backgroud.Application_Rest_url;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{
    public static Retrofit getRetrofi()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Application_Rest_url.getUrl()).client(okHttpClient).build();

        return retrofit;
    }

    public static UserService getService()
    {
        UserService userService = getRetrofi().create(UserService.class);
        return userService;
    }
}
