package com.jarvis.speech;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Locale;

import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import edu.cmu.pocketsphinx.SpeechRecognizer;


/**
 * Created by admin on 22-Nov-17.
 */

public class SpeechManager {

    public static final String TAG = SpeechManager.class.getSimpleName();
    /* Used to handle permission request */
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    /* Named searches allow to quickly reconfigure the decoder */
    public static final String KWS_SEARCH = "wakeup";
    /* Keyword we are looking for to activate menu */
    public static final String KEYPHRASE = "jarvis";

    private Context mContext;
    private SpeechRecognizer mPocketSphinxRecognizer;
    private final AIConfiguration mDialogFlowConfig;
    private AIService mAIService;
    private TextToSpeech tts;

    public SpeechManager(Context context){
        mContext=context;
        mDialogFlowConfig = new AIConfiguration(
                "400beb7c88f24ff49a9e4db3b1fac6a8 ",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        mAIService = AIService.getService(mContext, mDialogFlowConfig);
        mAIService.setListener(new DialogflowRecognitionListener(this));
        tts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.UK);
                }
            }
        });
    }

    public void start(){
        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            new PocketSphinxSetupAsyncTask(this).execute();
        }
    }

    public void startDialogFlowRecognition(){
        mAIService.startListening();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mAIService.stopListening();
//            }
//        }, 10000);
    }

    public void speak(String response){
        tts.speak(response, TextToSpeech.QUEUE_FLUSH, null);
    }

    public SpeechRecognizer getmPocketSphinxRecognizer() {
        return mPocketSphinxRecognizer;
    }

    public void setmPocketSphinxRecognizer(SpeechRecognizer mPocketSphinxRecognizer) {
        this.mPocketSphinxRecognizer = mPocketSphinxRecognizer;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void destroy(){
        mPocketSphinxRecognizer.cancel();
        mPocketSphinxRecognizer.shutdown();
        mAIService.cancel();
        mAIService.stopListening();
        tts.stop();
        tts.shutdown();
    }
}


