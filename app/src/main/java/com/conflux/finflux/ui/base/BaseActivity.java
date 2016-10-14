package com.conflux.finflux.ui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.conflux.finflux.FinfluxApplication;
import com.conflux.finflux.R;
import com.conflux.finflux.injection.component.ActivityComponent;
import com.conflux.finflux.injection.component.ConfigPersistentComponent;
import com.conflux.finflux.injection.component.DaggerConfigPersistentComponent;
import com.conflux.finflux.injection.module.ActivityModule;
import com.conflux.finflux.ui.base.service.BaseActivityCallback;
import com.conflux.finflux.util.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;


/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public class BaseActivity extends AppCompatActivity implements BaseActivityCallback, NavigationView.OnNavigationItemSelectedListener{

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();
    protected Toolbar toolbar;
    private ProgressDialog progress;
    private DrawerLayout mDrawerLayout;


    private ActivityComponent mActivityComponent;
    private long mActivityId;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(FinfluxApplication.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.d(getClass().getSimpleName(),"The item selected is "+item.toString());
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }

    @Override
    public void showProgress(String message) {
        if (progress == null) {
            progress = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
        }
        progress.setMessage(message);
        progress.show();
    }

    @Override
    public void setToolbarTitle(String title) {
        setActionBarTitle(title);
    }

    @Override
    public void hideProgress() {
        if (progress != null && progress.isShowing())
            progress.dismiss();
    }

    @Override
    public void logout() {

    }

    protected void showBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStateName = fragment.getClass().getName();
        boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStateName,
                0);

        if (!fragmentPopped && getSupportFragmentManager().findFragmentByTag(backStateName) ==
                null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerId, fragment, backStateName);
            if (addToBackStack) {
                transaction.addToBackStack(backStateName);
            }
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null && getTitle() != null) {
            setTitle(title);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        switch (item.getItemId()) {
           /* case R.id.item_collection:
                ApplicationAnalytics.sendEventInitializedLogs(FabricIoConstants.COLLECTION_SHEET);
                startCollectionSheet();
                break;
            case R.id.item_settings:
                startSettingsActivity();
                break;
            case R.id.item_logout:
                itinializeLogout();
                mNavigationView.setCheckedItem(R.id.item_logout);
                break;
            case R.id.item_offline:
                ApplicationAnalytics.sendEventInitializedLogs(FabricIoConstants.OFFLINE_MANAGEMENT);
                startOffilneActivity();
                break;
            case R.id.item_payemi:
                ApplicationAnalytics.sendEventInitializedLogs(FabricIoConstants.PAY_EMI);
                startPayEmiActivity();
                break;*/
        }
        return true;
    }

    public void startNavigationClickActivity(final Intent intent) {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);

            }
        }, 500);
    }
}
