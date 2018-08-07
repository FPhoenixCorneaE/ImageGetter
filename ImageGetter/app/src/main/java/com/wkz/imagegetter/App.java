package com.wkz.imagegetter;

import android.app.Application;

import com.wkz.imagegetter.utils.ContextUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.init(this);
    }
}
