package com.jarvis.voice;

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

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;
        String text = hypothesis.getHypstr();

        if (text.contains(VoiceManager.KEYPHRASE)) {
            mSpeechRecognizerManager.getmPocketSphinxRecognizer().cancel();
            mSpeechRecognizerManager.startDialogFlowRecognition();
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {

    }
}