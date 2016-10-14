package com.conflux.finflux.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.conflux.finflux.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;



@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "finflux_app_file";
    private static final String USER_ID = "preferences_user_id";
    private static final String TOKEN = "preferences_token";
    private static final String TENANT = "preferences_tenant";
    private static final String INSTANCE_URL = "preferences_instance";
    private static final String INSTANCE_DOMAIN = "preferences_domain";
    private static final String PORT = "preferences_port";
    private static final String ORGANIZATION = "organization_name";
    private static final String CAN_USE_DEFAULT_CERTIFICATE = "can_use_default_certificate";
    private static final String IS_AUTENTICATED_USER = "authenticated_user";
    private static final String PAIRED_DEVICE = "paired_device";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    private void putBoolean(String preferenceKey, boolean preferenceValue) {
        mPref.edit().putBoolean(preferenceKey, preferenceValue).apply();
    }

    private void putString(String preferenceKey, String preferenceValue) {
        mPref.edit().putString(preferenceKey, preferenceValue).apply();
    }

    private boolean getBoolean(String preferenceKey, boolean preferenceDefaultValue) {
        return mPref.getBoolean(preferenceKey, preferenceDefaultValue);
    }

    private String getString(String preferenceKey, String preferenceDefaultValue) {
        return mPref.getString(preferenceKey, preferenceDefaultValue);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void putOrganisation(final String name){
        putString(ORGANIZATION,name);
    }

    public String getOrganisation(){
        return getString(ORGANIZATION,"");
    }

}
