package com.tradexl.kidsstudy.dagger;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.tradexl.kidsstudy.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raghav on 10-Jan-18.
 */
@Module
public class AppModules {
    private String BASEURL;
    private String PREFNAME = "tradexl";
    private Application mApplication;
    private Activity activity;

    public AppModules(String baseUrl, Application mApplication) {
        this.BASEURL = baseUrl;
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return App.getStaticContext().getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
    }


}
