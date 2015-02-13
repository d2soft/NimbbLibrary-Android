<p align="center" >
  <img src="http://service.nimbb.com/Images/logo.png" alt="Nimbb.com" title="Nimbb.com">
</p>

NimbbLibrary-Android
================

Easily integrate video recording using Nimbb into your Android project.  [Nimbb](http://nimbb.com) is a turn-key all-inclusive service in the cloud that offers all the features you need to record, host and playback user generated videos inside your projects.  All videos are stored on our servers so that you focus only on the client side.  Refer to our [Pricing](http://nimbb.com/Help/Subscriptions.aspx) page for a complete feature list and price.

##Before you begin

To use the Nimbb Library for Android, first [create](http://nimbb.com/Account/Create.aspx) your Nimbb account.  A Silver (or higher) plan is required to be able to record or playback videos from Android.  [Contact](http://nimbb.com/Help/) us to get a trial period.

You also need to add an "Android application" entry in your Nimbb's [Developer Settings](http://nimbb.com/User/Dev/Settings.aspx).  Specify the package name of your Android project.

##How to install it

You need to add the NimbbLibrary JAR (NimbbLibrary/NimbbRecorderLibrary.jar) to your project libs folder.

###Supported SDK

This library support the SDK level 14 and higher (Android 4.x).

###Resources to add

You must add the following values to your res/values/strings.xml file. You can customize each strings if you want.

```xml
<!-- custom nimbb resources -->
<string name="nimbbNoCameraFound">Sorry… you must have a camera to use this app.</string>
<string name="nimbbRecordButton">Record</string>
<string name="nimbbStopRecordingButton">Stop</string>
<string name="nimbbSwitchButton">Switch</string>
```

You must copy the nimbbrecorder.xml (NimbbLibrary/nimbbrecorder.xml) into your res/layout folder.

##How to use it
Before you start recording videos, you need to initialize the player using your [developer key](http://nimbb.com/User/Dev/Key.aspx) from your Nimbb account.

```java
this.myNimbbManager = new NimbbManager("YOUR_DEV_KEY" ,context);
this.myNimbbManager.initNimbbPlayer(this.myNimbbManager.getMaxRecordLength(), NimbbManager.VideoQuality.High, handler);

//NimbbManager.VideoQuality definition
public enum VideoQuality {
  Low, Medium, High
}
```

When you want to send the video to the Nimbb server, you need to call the sendVideo method by using the filepath of the video.

```java
myPlayer.sendVideo(filePath, handler);
```

###Handlers interface
You must implement those interfaces in order to get a callback when a process is completed by the API.

####PlayerInitializationEvent
Used when we call the initialization service. You can add your custom logic if the configuration succeeded or failed.
```java
public void ConfigurationCompletedWithSuccess();
public void ConfigurationCompletedWithError(Exception errorDetails);
```
####VideoRecordEvent
Used when the recording process was stopped.
```java
public void VideoRecordStopped(String filePath, NimbbManager.RecordStatus status);
public void VideoRecordStoppedWithError(Exception ex);

//NimbbManager.RecordStatus definition
public enum RecordStatus {
    Stop, FileSizeLimitReached, LengthLimitReached, Error
}
```

####VideoSentEvent
Used when the video is sent to our server.
```java
public void VideoSentWithSuccess(String videoGuid);
public void VideoUploadProgression(int bytesWritten, int totalBytesWritten, long totalBytesExpectedToWrite);
public void VideoSentWithError(Exception errorDetails);

//NimbbManager.RecordStatus definition
public enum RecordStatus {
    Stop, FileSizeLimitReached, LengthLimitReached, Error
}
```


###Playback video
If you want to playback the video into your app, you need to call the  [Live/Play](http://nimbb.com/Doc/Dev/Service/Live/Play.aspx) function of the Nimbb service to get the video URL to use in your video player.


##Sample Project

You can download and test the Example project. This project will give you all the hints you need to use the library. Make sure to add the matching project's package name into the [settings](http://nimbb.com/User/Dev/Settings.aspx) of your Nimbb account (select "Android application").  Modify the value of kPublicKey in file ViewController.m to match your [public key](http://nimbb.com/User/Dev/Key.aspx).  Run on a physical device to record with the camera.
