package com.botpy.loanbanker.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by liuxuhui on 2017/6/23.
 */

public class AppConfig {

    private static Context context;

    public static void initContext(Application application){
        context = application.getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
