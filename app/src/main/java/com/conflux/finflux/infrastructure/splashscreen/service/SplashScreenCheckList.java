package com.conflux.finflux.infrastructure.splashscreen.service;

/**
 * Created by Praveen J U on 10/3/2016.
 */
public interface SplashScreenCheckList {
    void initalizeProgressBar();
    boolean isApplicationActivated();
    boolean isFirstLogin();
}
