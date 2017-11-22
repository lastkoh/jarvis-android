package com.jarvis;

import android.content.Context;

import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import edu.cmu.pocketsphinx.SpeechRecognizer;


/**
 * Created by admin on 22-Nov-17.
 */

public class SpeechRecognizerManager {

    public static final String TAG = SpeechRecognizerManager.class.getSimpleName();
    /* Named searches allow to quickly reconfigure the decoder */
    public static final String KWS_SEARCH = "wakeup";
    /* Keyword we are looking for to activate menu */
    public static final String KEYPHRASE = "jarvis";

    private Context mContext;
    private SpeechRecognizer mPocketSphinxRecognizer;
    private final AIConfiguration mDialogFlowConfig;
    private AIService mAIService;

    public SpeechRecognizerManager(Context context){
        mContext=context;
        mDialogFlowConfig = new AIConfiguration(
                "400beb7c88f24ff49a9e4db3b1fac6a8 ",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        mAIService = AIService.getService(mContext, mDialogFlowConfig);
        mAIService.setListener(new DialogflowRecognitionListener(this));
    }

    public void start(){
        new PocketSphinxSetupAsyncTask(this).execute();
    }

    public void restartSearch(String searchName) {
        mPocketSphinxRecognizer.stop();
        mPocketSphinxRecognizer.startListening(searchName);
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
}


