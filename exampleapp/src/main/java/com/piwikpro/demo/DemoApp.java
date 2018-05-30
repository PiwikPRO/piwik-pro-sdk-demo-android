/*
 * Android SDK for Piwik
 *
 * @link https://github.com/piwik/piwik-android-sdk
 * @license https://github.com/piwik/piwik-sdk-android/blob/master/LICENSE BSD-3 Clause
 */

package com.piwikpro.demo;

import android.os.Build;
import android.os.StrictMode;

import pro.piwik.sdk.Tracker;
import pro.piwik.sdk.TrackerConfig;
import pro.piwik.sdk.extra.DownloadTracker;
import pro.piwik.sdk.extra.PiwikApplication;
import pro.piwik.sdk.extra.TrackHelper;
import timber.log.Timber;

public class DemoApp extends PiwikApplication {

    private String host = "https://demoaccess.piwik.pro/";
    private String siteId = "3e7e6ab9-d605-42b0-ac1b-cdf5bb5e216f";

    //Not Overridden!!!  mPiwikDemoTracker is a new object for that allows change Host and siteId during app works
    private Tracker mPiwikDemoTracker;

    @Override
    public TrackerConfig onCreateTrackerConfig() {
        return TrackerConfig.createDefault(host, siteId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        initPiwik();
    }


    private void initPiwik() {
        // Print debug output when working on an app.
        Timber.plant(new Timber.DebugTree());

        TrackHelper.track().screens(this).with(getTracker());

        // When working on an app we don't want to skew tracking results.
//        getPiwik().setDryRun(BuildConfig.DEBUG);

        // If you want to set a specific userID other than the random UUID token, do it NOW to ensure all future actions use that token.
        // Changing it later will track new events as belonging to a different user.
        // String userEmail = ....preferences....getString
        // getTracker().setUserId(userEmail);

        // Track this app install, this will only trigger once per app version.
        // i.e. "https://demoaccess.piwik.pro:1/185DECB5CFE28FDB2F45887022D668B4"
        TrackHelper.track().download().identifier(new DownloadTracker.Extra.ApkChecksum(this)).with(getTracker());
    }

    @Override
    public void onLowMemory() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH && mPiwikDemoTracker != null) {
            mPiwikDemoTracker.dispatch();
        }
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        if ((level == TRIM_MEMORY_UI_HIDDEN || level == TRIM_MEMORY_COMPLETE) && mPiwikDemoTracker != null) {
            mPiwikDemoTracker.dispatch();
        }
        super.onTrimMemory(level);
    }

    //Not Overridden!!! mPiwikDemoTracker is a new object for that allows change Host and siteId during app works
    public synchronized Tracker getTracker() {
        if (mPiwikDemoTracker == null)
            mPiwikDemoTracker = getPiwik().newTracker(onCreateTrackerConfig());
        return mPiwikDemoTracker;
    }

    public synchronized Tracker getNewTracker() {
        mPiwikDemoTracker = getPiwik().newTracker(onCreateTrackerConfig());
        return mPiwikDemoTracker;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }


}
