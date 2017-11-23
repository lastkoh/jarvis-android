package com.jarvis.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.jarvis.Constants;
import com.jarvis.voice.VoiceForegroundService;

/**
 * Created by admin on 23-Nov-17.
 */

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive");

        if(intent.getAction() == null){
            return;
        }

        switch (intent.getAction()){
            case Intent.ACTION_BOOT_COMPLETED:
                Log.i(TAG, "onReceive onBoot");
                // Check if user has given permission to record audio
                int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    if(!VoiceForegroundService.isStarted){
                        Intent voiceForegroundService = new Intent(context, VoiceForegroundService.class);
                        voiceForegroundService.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                        context.startService(voiceForegroundService);
                    }
                }
                break;
            default:
                break;
        }
    }
}
