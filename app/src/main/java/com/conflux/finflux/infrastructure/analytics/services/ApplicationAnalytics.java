package com.conflux.finflux.infrastructure.analytics.services;

import com.conflux.finflux.data.local.PreferencesHelper;
import com.conflux.finflux.infrastructure.analytics.data.FabricIoConstants;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.LoginEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by praveen on 6/29/2016.
 */
@Singleton
public class ApplicationAnalytics {
    PreferencesHelper preferencesHelper;

    @Inject
    public ApplicationAnalytics(PreferencesHelper preferencesHelper)
    {
        this.preferencesHelper = preferencesHelper;
    }

    public void sendActivationStatus(String activationStatus,String activationKey){
        Answers.getInstance().logCustom(new CustomEvent(FabricIoConstants.ACTIVATION)
                .putCustomAttribute(FabricIoConstants.ACTIVATION_STATUS,activationStatus)
                .putCustomAttribute(FabricIoConstants.ACTIVATION_KEY,activationKey));
    }

    public void sendApplicationLaunchedInformation(String applicationActiveStatus){
        Answers.getInstance().logCustom(new CustomEvent(FabricIoConstants.APPLICATION_LAUNCED)
                .putCustomAttribute(FabricIoConstants.ACTIVATION_STATUS, applicationActiveStatus));
    }

    public void sendLoginStatus(boolean status,String username,String message){
        Answers.getInstance().logLogin(new LoginEvent()
                .putMethod("Login")
                .putSuccess(status)
                .putCustomAttribute(FabricIoConstants.USERNAME, username)
                .putCustomAttribute(FabricIoConstants.MESSAGE, message)
                .putCustomAttribute(FabricIoConstants.ORGANIZATION_NAME, preferencesHelper.getOrganisation()));
    }

    public void sendEventLogs(String eventName,String message){
        String organizationName=preferencesHelper.getOrganisation();
        Answers.getInstance().logCustom(new CustomEvent(organizationName)
                .putCustomAttribute(eventName,message));
    }

    public void sendEventInitializedLogs(String eventName){
        String organizationName=preferencesHelper.getOrganisation();
        Answers.getInstance().logCustom(new CustomEvent(organizationName)
                .putCustomAttribute(eventName, FabricIoConstants.INITIALIZED));
    }

    public void sendEventStartedLogs(String eventName){
        String organizationName=preferencesHelper.getOrganisation();
        Answers.getInstance().logCustom(new CustomEvent(organizationName)
                .putCustomAttribute(eventName, FabricIoConstants.STARTED));
    }


}
