# Demo Application Piwik Android SDK

## Description

Example of using the Piwik Tracking SDK for Android

## Requirements
 
In order to compile demo app by yourself proceed following steps:

* Install and configure [Android Studio](http://developer.android.com/sdk/installing/studio.html) and [Android SDK Packages](http://developer.android.com/sdk/installing/adding-packages.html)
* Clone Piwik Android SDK repo `git clone git@github.com:piwik/piwik-sdk-android.git`
* Open cloned folder in Android Studio as new project and configure Gradle
* In terminal run `./gradlew :exampleapp:assembleDebug`
* Copy .apk from `exampleapp/build/outputs/apk/exampleapp-debug.apk` to Your device and open it
    
If You want to run application:

* configure Android Emulator in AVD Manager (Ensure You've downloaded ARM System image and Builds Tools in SDK Manager)
* run "exampleapp" by clicking __play__ icon or "Ctr+Shift+A"

    
## Piwik dashboard 

Statistic of demo application usage could be found [here](http://beacons.testing.piwik.pro/).

## Changelog

### Version 1.0.1

Application supports tracking of screen views, goals and unhandled exceptions (analytics part). As well app supports data manager events.
There's also simple settings activity with customizable "dry run", "opt out" and "session timeout" params.

## Support

Please direct any feedback to
https://github.com/piwik/piwik-sdk-android/issues
