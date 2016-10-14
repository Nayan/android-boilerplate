package com.conflux.finflux.ui.base.service;

/**
 * Created by Praveen J U on 10/3/2016.
 */
public interface BaseActivityCallback {
    void showProgress(String message);

    void setToolbarTitle(String title);

    void hideProgress();

    void logout();
}
