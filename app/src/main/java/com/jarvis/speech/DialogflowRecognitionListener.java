package com.jarvis.speech;

import android.util.Log;

import ai.api.AIListener;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

/**
 * Created by admin on 22-Nov-17.
 */

public class DialogflowRecognitionListener implements AIListener {
    private static  final String TAG = DialogflowRecognitionListener.class.getSimpleName();

    private SpeechManager mSpeechRecognizerManager;

    public DialogflowRecognitionListener(SpeechManager speechRecognizerManager){
        this.mSpeechRecognizerManager = speechRecognizerManager;
    }

    @Override
    public void onResult(AIResponse result) {
        Result returnResult = result.getResult();
        String speech = returnResult.getFulfillment().getSpeech();
        mSpeechRecognizerManager.speak(speech);
        mSpeechRecognizerManager.getmPocketSphinxRecognizer()
                .startListening(SpeechManager.KWS_SEARCH);
    }

    @Override
    public void onError(AIError error) {
        Log.i(TAG, "Dialogflow onError: " + error.getMessage());
        mSpeechRecognizerManager.getmPocketSphinxRecognizer()
                .startListening(SpeechManager.KWS_SEARCH);
    }

    @Override
    public void onAudioLevel(float level) {
        Log.i(TAG, "Dialogflow onAudioLevel: " + level);
    }

    @Override
    public void onListeningStarted() {
        Log.i(TAG,"Dialogflow onListeningStarted");
    }

    @Override
    public void onListeningCanceled() {
        Log.i(TAG,"Dialogflow onListeningCanceled");
    }

    @Override
    public void onListeningFinished() {
        Log.i(TAG,"Dialogflow onListeningFinished");
    }
}
