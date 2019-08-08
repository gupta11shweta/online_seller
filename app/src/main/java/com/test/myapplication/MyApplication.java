package com.test.myapplication;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;


public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static MyApplication mInstance;

    public MyApplication() {
        mInstance=this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

    public static Context getApplicationCtx() {
        return mInstance.getApplicationContext();
    }

    public static Application getApplication(){
        return mInstance;
    }
}
