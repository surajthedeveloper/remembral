package com.birthday.remembral;


import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.birthday.remembral.flurry.FlurryImpl;
import com.squareup.leakcanary.LeakCanary;

public class RemembralApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        FlurryImpl.flurryInitializationInApplication(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        enabledStrictMode();
        LeakCanary.install(this);
        // Normal app init code...
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build());
    }
}
