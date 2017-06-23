package com.botpy.loanbanker.api.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuxuhui on 2017/6/23.
 */

public class HeaderInterceptor implements Interceptor {

    private static final String HEADER_APP_ID     = "X-Loan-AppID";
    private static final String HEADER_DEVICE_ID  = "X-Loan-DeviceID";
    private static final String HEADER_TIME_STAMP = "X-Loan-Timestamp";
    private static final String HEADER_SIGNATURE  = "X-Loan-Signature";
    private static final String HEADER_AUTH       = "Authorization";
    private static final String HEADER_USER_AGENT = "User-Agent";

    @Override
    public Response intercept(Chain chain) throws IOException {


        Request.Builder builder = chain.request().newBuilder();
        return null;
    }
}
