package com.jarvis.voice;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;

import com.jarvis.Constants;

import java.util.Locale;

import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import edu.cmu.pocketsphinx.SpeechRecognizer;


/**
 * Created by admin on 22-Nov-17.
 */

public class VoiceManager {

    public static final String TAG = VoiceManager.class.getSimpleName();
    private Context mContext;
    private SpeechRecognizer mPocketSphinxRecognizer;
    private final AIConfiguration mDialogFlowConfig;
    private AIService mAIService;
    private TextToSpeech tts;

    public VoiceManager(Context context){
        mContext=context;
        mDialogFlowConfig = new AIConfiguration(
                Constants.VOICE.CLIENT_ACCESS_TOKEN,
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

    public void init(){
        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            new PocketSphinxSetupAsyncTask(this).execute();
        }
    }

    public void pocketSphinxStartListening(){
        mPocketSphinxRecognizer.startListening(Constants.VOICE.KWS_SEARCH);
    }

    public void pocketSphinxCancelListening(){
        mPocketSphinxRecognizer.cancel();
    }

    public void dialogFlowStartListening(){
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

    public void shutdown(){
        mPocketSphinxRecognizer.cancel();
        mPocketSphinxRecognizer.shutdown();
        mAIService.cancel();
        mAIService.stopListening();
        tts.stop();
        tts.shutdown();
    }
}


