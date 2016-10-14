package com.conflux.finflux.injection.component;

import android.app.Application;
import android.content.Context;

import com.conflux.finflux.data.DataManager;
import com.conflux.finflux.data.SyncService;
import com.conflux.finflux.data.local.DatabaseHelper;
import com.conflux.finflux.data.local.PreferencesHelper;
import com.conflux.finflux.data.remote.RibotsService;
import com.conflux.finflux.infrastructure.analytics.services.ApplicationAnalytics;
import com.conflux.finflux.injection.ApplicationContext;
import com.conflux.finflux.injection.module.ApplicationModule;
import com.conflux.finflux.util.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext
    Context context();
    Application application();
    RibotsService ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();
    ApplicationAnalytics applicationAnalytics();

}
