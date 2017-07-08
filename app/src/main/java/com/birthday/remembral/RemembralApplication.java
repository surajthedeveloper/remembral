package com.birthday.remembral;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.birthday.remembral.flurry.FlurryImpl;

public class RemembralApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        FlurryImpl.flurryInitializationInApplication(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
