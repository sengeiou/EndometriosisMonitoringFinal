package com.benlefevre.endometriosismonitoring;

import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
