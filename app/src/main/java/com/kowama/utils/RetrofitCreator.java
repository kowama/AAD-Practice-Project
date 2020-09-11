package com.kowama.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {
    private static Retrofit retrofit ;
    private static Gson gson ;
    private static String UPLOAD_BASE_URL ="https://docs.google.com/forms/d/e/";
    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(UPLOAD_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
