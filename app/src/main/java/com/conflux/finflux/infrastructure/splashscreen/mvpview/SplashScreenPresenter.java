package com.conflux.finflux.infrastructure.splashscreen.mvpview;

import com.conflux.finflux.data.DataManager;
import com.conflux.finflux.data.local.PreferencesHelper;
import com.conflux.finflux.infrastructure.activation.data.Actication;
import com.conflux.finflux.infrastructure.activation.database.ActivationDatabaseHelper;
import com.conflux.finflux.ui.base.BasePresenter;
import com.conflux.finflux.ui.main.MainMvpView;
import com.conflux.finflux.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen J U on 10/10/2016.
 */
public class SplashScreenPresenter extends BasePresenter<SplashScreenMvpView>{

    private Subscription mSubscription;
    private final ActivationDatabaseHelper dataManager;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public SplashScreenPresenter(ActivationDatabaseHelper dataManager, PreferencesHelper preferencesHelper){
        this.dataManager = dataManager;
        this.preferencesHelper = preferencesHelper;
    }

    public void attachView(SplashScreenMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void checkIsAppActivated(){
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = dataManager.getActivation()
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Actication>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Actication> actications) {
                        if(actications.size()>0){
                            getMvpView().isActivated(true);
                        }else {
                            getMvpView().isActivated(false);
                        }
                    }
                });
    }
}
