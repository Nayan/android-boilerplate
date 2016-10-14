package com.conflux.finflux;

import android.app.Application;
import android.content.Context;
import android.support.compat.BuildConfig;

import com.conflux.finflux.injection.component.ApplicationComponent;

import com.conflux.finflux.injection.component.DaggerApplicationComponent;
import com.conflux.finflux.injection.module.ApplicationModule;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


public class FinfluxApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Fabric.with(this, new Crashlytics());
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Fabric.with(this, new Crashlytics());

       /*     Stetho.initialize(

                    Stetho.newInitializerBuilder(this);
                            *//*.enableDumpapp(Stetho.defaultDumperPluginsProvider(this)).build());*//*
                           *//* .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())*//**/

        }
    }

    public static FinfluxApplication get(Context context) {
        return (FinfluxApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
