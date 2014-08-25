package com.nimbb.apidemo;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import com.nimbb.nimbbapidemo.R;
import com.nimbb.recorder.NimbbManager;
import com.nimbb.recorder.handlers.VideoRecordEvent;


/********************************************
 * Nimbb Player API - Android - Version 1.0
 * ©2014 D2Soft Technologies Inc
 *********************************************/
public class DemoVideoRecordHandler implements VideoRecordEvent {
    NimbbManager myPlayer;
    MainActivity currentContext;
    EditText logBox;

    public DemoVideoRecordHandler(MainActivity currentContext, NimbbManager currentNimbbPlayer) {
        this.myPlayer = currentNimbbPlayer;
        this.currentContext = currentContext;
        logBox = (EditText)this.currentContext.findViewById(R.id.logText);
    }

    @Override
    public void VideoRecordStopped(String filePath, NimbbManager.RecordStatus status) {

        try {
            if(status == NimbbManager.RecordStatus.FileSizeLimitReached) {
                Toast.makeText(currentContext, R.string.fileSizeMaxReached, Toast.LENGTH_LONG).show();
            } else if(status == NimbbManager.RecordStatus.LengthLimitReached) {
                Toast.makeText(currentContext, R.string.durationReached, Toast.LENGTH_LONG).show();
            }

            DemoVideoSentHandler handler = new DemoVideoSentHandler(currentContext);
            myPlayer.sendVideo(filePath, handler);
            logBox.setText("Upload in progress...");
        }
        catch (Exception e) {
            this.VideoRecordStoppedWithError(e);
        }
    }

    @Override
    public void VideoRecordStoppedWithError(Exception errorDetails) {
        Log.e("DemoVideoRecordHandler", errorDetails.getLocalizedMessage());
        logBox.setText("Error while trying to record the video: " + errorDetails.getMessage());
    }
}
