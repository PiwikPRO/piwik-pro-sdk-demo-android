# Piwik PRO Android SDK Demo Application 

## Description

Example of using the Piwik PRO Tracking SDK for Android

## Requirements

In order to compile the demo app by yourself, complete the following steps:

* Install and configure [Android Studio](http://developer.android.com/sdk/installing/studio.html) and [Android SDK Packages](http://developer.android.com/sdk/installing/adding-packages.html)
* Clone this repo: 

        git clone https://github.com/PiwikPRO/piwik-pro-sdk-demo-android.git
        
* Open the cloned folder in Android Studio as a new project and configure Gradle
* In the terminal, run `./gradlew :exampleapp:assembleDebug`
* Copy the .apk file from `exampleapp/build/outputs/apk/exampleapp-debug.apk` to your device and open it

If You want to run application:

* configure Android Emulator in AVD Manager (make sou've downloaded a system image and Builds Tools in the SDK Manager)
* run "exampleapp" by clicking the __play__ icon or "Ctrl+Shift+A"

## Checking the data in Piwik PRO

To connect the app to your Piwik PRO instance, go to Settings and set your instance URL and website ID. Use "Update Host and Site ID" to save your settings. Then, you can log into your instance and go to "Analytics -> Settings -> Tracker debugger" to see the data in real-time.

## Changelog

### Version 1.0.0

The app supports tracking Analytics events:

* Screen Views
* Goals
* Custom Events
* Custom Dimensions
* Custom Variables
* Downloads
* Outlinks
* Search
* Content Impressions
* Exceptions
* Ecommerce: Items, Cart update, Orders
* Social Interactions
* Campaigns

The app also supports using the Audience Manager methods in the SDK:

* Track user profile attributes
* Read user profile attributes
* Check Audience membership

There's also a Settings activity with customizable "dry run", "opt out" and "session timeout", "prefixing" parameters.

## Support

Please direct any feedback to
https://github.com/PiwikPRO/piwik-pro-sdk-demo-android/issues
