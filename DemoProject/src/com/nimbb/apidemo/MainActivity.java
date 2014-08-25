package com.nimbb.apidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.nimbb.nimbbapidemo.R;
import com.nimbb.recorder.NimbbManager;
import com.nimbb.recorder.RecorderActivity;


public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private NimbbManager myNimbbManager;
    private EditText logText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setupPlayer();
    }

    private void setupPlayer() {
        logText = (EditText) findViewById(R.id.logText);
        logText.setText("Please wait...");

        Button recordButton = (Button)findViewById(R.id.recordButton);
        recordButton.setEnabled(false);

        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startVideoRecording();
            }
        });


        DemoPlayerInitHandler handler = new DemoPlayerInitHandler(this);
        this.myNimbbManager = new NimbbManager("YOUR_DEV_KEY" ,this);
        this.myNimbbManager.initNimbbPlayer(this.myNimbbManager.getMaxRecordLength(), NimbbManager.VideoQuality.High, handler);
    }

    private void startVideoRecording() {
        // you can use the default recording camera provided by the API.
        // You can use a custom VideoRecordEvent handler or use the ActivityResult
        DemoVideoRecordHandler recordHandler = new DemoVideoRecordHandler(this, myNimbbManager);
        myNimbbManager.startRecording(this, recordHandler);

        // you could implement your own camera activity, but make sure to respect the following constraints:
        //   - your video must be in the following resolution (720x960, 720x480 or 320x240)
        //   - you must limit the length of the video. (You can have the length value (seconds) returned by the API based on your account by calling: myNimbbManager.getMaxRecordLength();)
        //   - you must limit the size of your video. (You can have the size value (bytes) returned by the API based on your account by calling: myNimbbManager.getMaxRecordSize();)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            // you must intercept the recording activity result
            case NimbbManager.NIMBB_RECORDING_INTENT_CODE:
                if (resultCode == RESULT_OK) {
                    this.logText.setText("Video recording completed...");
                    Bundle res = data.getExtras();
                    String result = res.getString(NimbbManager.NIMBB_RECORDING_INTENT_FILEPATH);
                    NimbbManager.RecordStatus recordStatus =  (NimbbManager.RecordStatus)res.getSerializable(NimbbManager.NIMBB_RECORDING_INTENT_STATUS);

                    Log.d("NimbbDemo", "File path: "+ result);
                    Log.d("NimbbDemo", "Record status: " + recordStatus);
//                    try {
//                        InputStream stream = this.getContentResolver().openInputStream(data.getData());
//                        DemoVideoSentHandler videoSentHandler = new DemoVideoSentHandler(this);
//
//                        this.myNimbbManager.sendVideo(stream, videoSentHandler);
//
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                break;
        }
    }
}
