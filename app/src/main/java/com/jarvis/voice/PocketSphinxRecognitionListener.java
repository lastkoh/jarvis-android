package com.jarvis.voice;

import android.util.Log;

import com.jarvis.Constants;

import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;

/**
 * Created by admin on 22-Nov-17.
 */

public class PocketSphinxRecognitionListener implements RecognitionListener {
    private static final String TAG = PocketSphinxRecognitionListener.class.getSimpleName();
    private VoiceManager mSpeechRecognizerManager;

    public PocketSphinxRecognitionListener(VoiceManager speechRecognizerManager) {
        this.mSpeechRecognizerManager = speechRecognizerManager;
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningSpeech");

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;
        String text = hypothesis.getHypstr();

        if (text.contains(Constants.VOICE.WAKE_UP_KEYPHRASE)) {
            mSpeechRecognizerManager.pocketSphinxCancelListening();
            mSpeechRecognizerManager.dialogFlowStartListening();
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {

    }

    @Override
    public void onError(Exception e) {
        Log.i(TAG,"onError:" + e.getMessage());

    }

    @Override
    public void onTimeout() {

    }
}