package com.tradexl.kidsstudy.dagger;

import com.tradexl.kidsstudy.ABCDActivity;
import com.tradexl.kidsstudy.DashActivity;
import com.tradexl.kidsstudy.TableActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Raghav on 10-Jan-18.
 */
@Singleton
@Component(modules = AppModules.class)
public interface AppComponents  {
    void inject(DashActivity activity);
    void inject(TableActivity activity);
    void inject(ABCDActivity activity);

}
