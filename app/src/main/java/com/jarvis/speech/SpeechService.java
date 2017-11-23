package com.jarvis.speech;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by admin on 23-Nov-17.
 */

public class SpeechService extends Service{
    private SpeechManager mSpeechManager;
    private boolean isStarted;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!isStarted){
            mSpeechManager = new SpeechManager(getApplicationContext());
            mSpeechManager.start();
            isStarted = true;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSpeechManager.destroy();
    }
}
