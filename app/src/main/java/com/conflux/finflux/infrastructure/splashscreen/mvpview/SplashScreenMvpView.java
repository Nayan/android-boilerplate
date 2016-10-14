package com.conflux.finflux.infrastructure.splashscreen.mvpview;

import com.conflux.finflux.data.model.Ribot;
import com.conflux.finflux.ui.base.MvpView;

import java.util.List;

/**
 * Created by Praveen J U on 10/10/2016.
 */
public interface SplashScreenMvpView extends MvpView {
    void isActivated(Boolean isActivated);

    void isFirstLogin(Boolean isFirstLogin);

}
