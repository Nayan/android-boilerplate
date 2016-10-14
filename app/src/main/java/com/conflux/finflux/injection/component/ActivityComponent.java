package com.conflux.finflux.injection.component;

import com.conflux.finflux.infrastructure.splashscreen.activity.SplashScreen;
import com.conflux.finflux.injection.PerActivity;
import com.conflux.finflux.injection.module.ActivityModule;
import com.conflux.finflux.ui.main.MainActivity;

import dagger.Subcomponent;


/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(SplashScreen splashScreen);

}
