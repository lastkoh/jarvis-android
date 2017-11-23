package com.jarvis.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jarvis.speech.SpeechService;

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
                context.startService(new Intent(context, SpeechService.class));
                break;
            default:
                break;
        }
    }
}
