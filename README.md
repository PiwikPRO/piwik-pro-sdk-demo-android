# Demo Application Piwik PRO Android SDK

## Description

Example of using the Piwik PRO Tracking SDK for Android

## Requirements
 
In order to compile demo app by yourself proceed following steps:

* Install and configure [Android Studio](http://developer.android.com/sdk/installing/studio.html) and [Android SDK Packages](http://developer.android.com/sdk/installing/adding-packages.html)
* Clone Piwik PRO Android SDK demo repo `git clone git@github.com:PiwikPRO/piwik-pro-sdk-demo-android.git`
* Open cloned folder in Android Studio as new project and configure Gradle
* In terminal run `./gradlew :exampleapp:assembleDebug`
* Copy .apk from `exampleapp/build/outputs/apk/exampleapp-debug.apk` to Your device and open it
    
If You want to run application:

* configure Android Emulator in AVD Manager (Ensure You've downloaded ARM System image and Builds Tools in SDK Manager)
* run "exampleapp" by clicking __play__ icon or "Ctr+Shift+A"

    
## Piwik PRO dashboard

Statistic of demo application usage could be found [here](https://demoaccess.piwik.pro).

## Changelog

### Version 1.0.0

Application supports tracking of analytics events:
* screen views
* custom vars
* events
* goals
* custom dimensions
* downloads
* outlinks
* search
* content impression
* exceptions
* ecommerce items, cart update, order
* social interaction
* advanced campaign

As app well app supports tracking audience manager events:
* track user profile attributes
* read user profile attributes
* check audience membership

There's also settings activity with customizable "dry run", "opt out" and "session timeout", "prefixing" parameters.

## Support

Please direct any feedback to
https://github.com/PiwikPRO/piwik-pro-sdk-demo-android/issues
