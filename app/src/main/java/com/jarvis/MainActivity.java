package com.jarvis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jarvis.voice.VoiceForegroundService;

public class MainActivity extends AppCompatActivity {

    /* Used to handle permission request */
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }

        if(!VoiceForegroundService.isStarted){
            Intent voiceForegroundService = new Intent(getApplicationContext(), VoiceForegroundService.class);
            voiceForegroundService.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            getApplicationContext().startService(voiceForegroundService);
            VoiceForegroundService.isStarted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_RECORD_AUDIO:
                if(grantResults.length > 0){
                    if(!VoiceForegroundService.isStarted){
                        Intent voiceForegroundService = new Intent(getApplicationContext(), VoiceForegroundService.class);
                        voiceForegroundService.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                        getApplicationContext().startService(voiceForegroundService);
                        VoiceForegroundService.isStarted = true;
                    }
                }else{
                }
                break;
        }
    }
}
