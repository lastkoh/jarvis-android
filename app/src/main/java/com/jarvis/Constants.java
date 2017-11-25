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

    public interface VOICE {
        /* Named searches allow to quickly reconfigure the decoder */
        String KWS_SEARCH = "wakeup";
        /* Keyword we are looking for to activate menu */
        String WAKE_UP_KEYPHRASE = "ok jarvis";
        /* PocketSphinx Keyword Phrase Threshold */
        float THRESHOLD = 1e-40f;
        /* DialogFlow Client Access Token */
        String CLIENT_ACCESS_TOKEN = "400beb7c88f24ff49a9e4db3b1fac6a8";
    }
}
