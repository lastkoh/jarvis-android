package com.jarvis;

/**
 * Created by admin on 23-Nov-17.
 */

public class Constants {
    public interface ACTION {
        String MAIN_ACTION = "com.jarvis.voice.action.main";
        String STARTFOREGROUND_ACTION = "com.jarvis.voice.action.startforeground";
        String STOPFOREGROUND_ACTION = "com.jarvis.voice.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
        String CHANNEL_ID = "N_JARVIS";
    }
}
