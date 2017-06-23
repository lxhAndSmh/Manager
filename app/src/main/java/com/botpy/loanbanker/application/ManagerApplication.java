package com.botpy.loanbanker.application;

import android.app.Application;

/**
 * Created by liuxuhui on 2017/6/23.
 */

public class ManagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppConfig.getContext();
    }
}
