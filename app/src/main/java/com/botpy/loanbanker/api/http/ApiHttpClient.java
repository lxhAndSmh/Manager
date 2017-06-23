package com.botpy.loanbanker.api.http;

import com.botpy.loanbanker.BuildConfig;
import com.botpy.loanbanker.api.LoanApi;
import com.botpy.loanbanker.api.interceptor.HeaderInterceptor;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by liuxuhui on 2017/6/21.
 */

public class ApiHttpClient {

    private final static int TIME_OUT = 5;

    private static ApiHttpClient apiHttpClient;

    private LoanApi loanApi;

    public static ApiHttpClient getInstance(){
        if(apiHttpClient == null){
            apiHttpClient = new ApiHttpClient();
        }
        return apiHttpClient;
    }

    public LoanApi getLoanApi(){
        return loanApi == null ? configRetrofit(LoanApi.class) : loanApi;
    }

    private <T> T configRetrofit(Class<T> service){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BuildConfig.API_BASE_URL)
                .client(confitOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(service);
    }

    private OkHttpClient confitOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HeaderInterceptor())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        return builder.build();
    }
}
