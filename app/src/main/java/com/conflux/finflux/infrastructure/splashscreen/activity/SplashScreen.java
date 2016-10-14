package com.conflux.finflux.infrastructure.splashscreen.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.conflux.finflux.R;
import com.conflux.finflux.data.DataManager;
import com.conflux.finflux.data.local.PreferencesHelper;
import com.conflux.finflux.infrastructure.activation.data.Actication;
import com.conflux.finflux.infrastructure.analytics.services.ApplicationAnalytics;
import com.conflux.finflux.infrastructure.splashscreen.mvpview.SplashScreenMvpView;
import com.conflux.finflux.infrastructure.splashscreen.mvpview.SplashScreenPresenter;
import com.conflux.finflux.infrastructure.splashscreen.service.SplashScreenCheckList;
import com.conflux.finflux.ui.base.BaseActivity;
import com.conflux.finflux.util.Logger;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen J U on 10/3/2016.
 */
public class SplashScreen extends BaseActivity implements SplashScreenCheckList, SplashScreenMvpView{

    private final String TAG = getClass().getSimpleName();

    //bind view components starts here
    @BindView(R.id.fullscreen_content) ImageView imageViewLogo;
    @BindView(R.id.progressBar)ProgressBar progressBar;

    @Inject
    PreferencesHelper mPreferencesHelper;
    @Inject
    ApplicationAnalytics applicationAnalytics;

    @Inject
    DataManager dataManager;

    //
    private ConditionVariable conditionVariable;
    private Handler handler = new Handler();
    int progressStatus = 0;


    TranslateAnimation.AnimationListener animationListener = new TranslateAnimation.AnimationListener(){

        @Override
        public void onAnimationStart(Animation animation) {
            Logger.d(TAG,"animation start");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Logger.d(TAG,"animation end");
            initalizeProgressBar();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        activityComponent().inject(this);
        applicationAnalytics.sendApplicationLaunchedInformation(getString(R.string.app_start));
        ButterKnife.bind(this);
        TranslateAnimation contentViewAnimate = new TranslateAnimation(0.0f, 0.0f,
                0.0f, -300.0f);
        contentViewAnimate.setDuration(2000);
        contentViewAnimate.setFillAfter(true);
        contentViewAnimate.setAnimationListener(animationListener);
        imageViewLogo.startAnimation(contentViewAnimate);
        mPreferencesHelper.putOrganisation("Conflux Technologies");
        Logger.d(TAG,"The organization is "+mPreferencesHelper.getOrganisation());
    }

    @Override
    public void initalizeProgressBar() {
        Logger.e(TAG, "Initialize The Progress Bar");
        progressBar.setVisibility(View.VISIBLE);
        conditionVariable = new ConditionVariable();
        incrementProgress();
    }

    private void incrementProgress(){
        progressBar.setVisibility(View.VISIBLE);
        ProgressAsynchronous progressAsynchronous =new ProgressAsynchronous();
        progressAsynchronous.execute();
    }
    @Override
    public boolean isApplicationActivated() {
        return true;
    }

    @Override
    public boolean isFirstLogin() {
        return true;
    }

    private void updateProgressBar(final Integer integer){
        handler.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(integer);
            }
        });
    }

    @Override
    public void isActivated(Boolean isActivated) {
        if(isActivated){
            //checkIsFirstLogin();
        }
    }

    @Override
    public void isFirstLogin(Boolean isFirstLogin) {

    }

    @Override
    public void showProgressbar(boolean b) {

    }

    private class ProgressAsynchronous extends AsyncTask<Void,Integer,Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            while (progressStatus < 100)
            {
                progressStatus += 1;
                updateProgressBar(progressStatus);
                try
                {
                    Thread.sleep(20);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                //startActivationActivity();
            }
        }
    }

}
