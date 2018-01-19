package com.tradexl.kidsstudy;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.tradexl.kidsstudy.dagger.AppComponents;
import com.tradexl.kidsstudy.dagger.AppModules;
import com.tradexl.kidsstudy.dagger.DaggerAppComponents;
import com.tradexl.kidsstudy.util.Constant;

/**
 * Created by Raghav on 02-Jan-18.
 */

public class App extends Application {
    private static Context context;
    private AppComponents mAppComponents;

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();

        mAppComponents = DaggerAppComponents.builder()
                .appModules(new AppModules(Constant.BASEURL, this))
                .build();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    public AppComponents getAppComponents() {
        return mAppComponents;
    }

    public static Context getStaticContext() {
        return App.context;
    }
}
