package com.jarvis.voice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.jarvis.Constants;
import com.jarvis.MainActivity;
import com.jarvis.R;

/**
 * Created by admin on 23-Nov-17.
 */

public class VoiceForegroundService extends Service{
    private VoiceManager mVoiceManager;
    public static boolean isStarted;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction() != null){
            switch (intent.getAction()){
                case Constants.ACTION.STARTFOREGROUND_ACTION:
                    mVoiceManager = new VoiceManager(getApplicationContext());
                    mVoiceManager.start();
                    showNotification();
                    break;
                case Constants.ACTION.STOPFOREGROUND_ACTION:
                    mVoiceManager.destroy();
                    stopForeground(true);
                    stopSelf();
            }
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVoiceManager.destroy();
    }

    private void showNotification(){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        // And now, building and attaching the Close button.
        Intent buttonCloseIntent = new Intent(this, VoiceForegroundService.class);
        buttonCloseIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pCloseIntent =
                PendingIntent.getService(this, 0, buttonCloseIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


        Notification notification = new NotificationCompat.Builder(this
                , Constants.NOTIFICATION_ID.CHANNEL_ID)
                .setContentTitle("Jarvis")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(R.drawable.ic_close_black_24dp, "Close",
                        pCloseIntent).build();
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
    }
}
