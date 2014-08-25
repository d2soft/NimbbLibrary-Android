package com.nimbb.apidemo;

import android.util.Log;
import android.widget.EditText;
import com.nimbb.nimbbapidemo.R;
import com.nimbb.recorder.handlers.VideoSentEvent;

/********************************************
 * Nimbb Player API - Android - Version 1.0
 * ©2014 D2Soft Technologies Inc
 *********************************************/
public class DemoVideoSentHandler implements VideoSentEvent {
    EditText logBox;
    MainActivity context;

    public DemoVideoSentHandler(MainActivity currentContext) {
        context = currentContext;
        logBox = (EditText)currentContext.findViewById(R.id.logText);
    }

    public void VideoSentWithSuccess(String videoGuid) {
        Log.d("DemoVideoSentHandler", "Your video guid is: " + videoGuid);
        logBox.setText("Video was uploaded. Your guid is: " + videoGuid);
    }

    public void VideoUploadProgression(int bytesWritten, int totalBytesWritten, long totalBytesExpectedToWrite) {
        final float percent = ((float)totalBytesWritten / (float)totalBytesExpectedToWrite)*100;
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("DemoVideoSentHandler", "Upload @ " + percent);
                logBox.setText(String.format("Upload progression: #[%1$d]", ((int)percent)));
            }
        });
    }

    public void VideoSentWithError(Exception errorDetails) {
        Log.e("DemoVideoSentHandler", "VideoSentWithError : " + errorDetails.getMessage());
        logBox.setText("Error while trying to send the video: " + errorDetails.getMessage());
    }
}
