package com.nimbb.apidemo;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.nimbb.nimbbapidemo.R;
import com.nimbb.recorder.handlers.PlayerInitializationEvent;

/********************************************
 * Nimbb Player API - Android - Version 1.0
 * ©2014 D2Soft Technologies Inc
 *********************************************/
public class DemoPlayerInitHandler implements PlayerInitializationEvent {
    EditText logBox;
    Button recordButton;

    public DemoPlayerInitHandler(MainActivity currentContext) {
        logBox = (EditText)currentContext.findViewById(R.id.logText);
        recordButton = (Button)currentContext.findViewById(R.id.recordButton);
    }

    public void ConfigurationCompletedWithSuccess() {
        Log.d("DemoPlayerInitHandler", "ConfigurationCompletedWithSuccess yay!");
        logBox.setText("Configuration is completed! You can record a video now!");
        recordButton.setEnabled(true);
    }

    public void ConfigurationCompletedWithError(Exception errorDetails) {
        Log.e("DemoPlayerInitHandler", "ConfigurationCompletedWithError : " + errorDetails.getMessage());

        logBox.setText("Error while trying to configure the player: " + errorDetails.getMessage());
    }
}
