# Piwik PRO Android SDK Demo Application 

## Description

Example of using the Piwik PRO Tracking SDK for Android

## Requirements

In order to compile the demo app by yourself, complete the following steps:

* Install and configure [Android Studio](http://developer.android.com/sdk/installing/studio.html) and [Android SDK Packages](http://developer.android.com/sdk/installing/adding-packages.html)
* Clone this repo

    git clone https://github.com/PiwikPRO/piwik-pro-sdk-demo-android.git

* Open the cloned folder in Android Studio as a new project and configure Gradle
* In the terminal, run `./gradlew :exampleapp:assembleDebug`
* Copy the .apk file from `exampleapp/build/outputs/apk/exampleapp-debug.apk` to Your device and open it

If You want to run application:

* configure Android Emulator in AVD Manager (Ensure You've downloaded ARM System image and Builds Tools in SDK Manager)
* run "exampleapp" by clicking __play__ icon or "Ctr+Shift+A"

## Checking the data in Piwik PRO

To connect your Piwik PRO instance to the app, go to Settings and set your instance URL and website ID. Then, click on "Update Host and Site ID" to save your settings. Log in to your instance and go to Analytics -> Tracker debugger to see the data in real-time.

## Changelog

### Version 1.0.0

The app supports tracking Analytics events:

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

The app also supports using the Audience Manager methods in the SDK:

* track user profile attributes
* read user profile attributes
* check audience membership

There's also a Settings activity with customizable "dry run", "opt out" and "session timeout", "prefixing" parameters.

## Support

Please direct any feedback to
https://github.com/PiwikPRO/piwik-pro-sdk-demo-android/issues
