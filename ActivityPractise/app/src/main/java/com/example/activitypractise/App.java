package com.example.activitypractise;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class App extends Application {
    // https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.updateLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // LocaleManager.updateLocale(this);
    }
}