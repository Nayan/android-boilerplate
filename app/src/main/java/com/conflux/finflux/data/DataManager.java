package com.conflux.finflux.data;

import com.conflux.finflux.data.local.DatabaseHelper;
import com.conflux.finflux.data.local.PreferencesHelper;
import com.conflux.finflux.infrastructure.activation.data.Actication;
import com.conflux.finflux.data.model.Ribot;
import com.conflux.finflux.data.remote.RibotsService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;


@Singleton
public class DataManager {

    private final RibotsService mRibotsService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(RibotsService ribotsService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mRibotsService = ribotsService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Ribot> syncRibots() {
        return mRibotsService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });
    }

    public void setActivation(){
        mDatabaseHelper.setActivation();
    }

    public Observable<Actication> getActication(){
        return mDatabaseHelper.getActivation();
    }

    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }

}
