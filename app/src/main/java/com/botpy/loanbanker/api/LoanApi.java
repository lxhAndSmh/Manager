package com.botpy.loanbanker.api;

import android.database.Observable;
import android.os.Build;

import com.botpy.loanbanker.api.model.CommonModel;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Created by liuxuhui on 2017/6/21.
 */

public interface LoanApi {

    /**
     * API版本
     */
   String VERSION = "/v1/";

    /**
     * 获取验证码
     */
    @POST(VERSION + "user/verify_code")
    Observable<CommonModel> getVerfyCode(@QueryMap Map<String, String> options);


}
