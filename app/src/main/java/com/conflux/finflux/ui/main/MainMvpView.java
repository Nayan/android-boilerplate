package com.conflux.finflux.ui.main;

import com.conflux.finflux.data.model.Ribot;
import com.conflux.finflux.ui.base.MvpView;

import java.util.List;



public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError();

}
