package com.jarvis;

import android.widget.Toast;

import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;

/**
 * Created by admin on 22-Nov-17.
 */

public class PocketSphinxRecognitionListener implements RecognitionListener {
    private SpeechRecognizerManager mSpeechRecognizerManager;

    public PocketSphinxRecognitionListener(SpeechRecognizerManager speechRecognizerManager) {
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
        if (text.contains(SpeechRecognizerManager.KEYPHRASE)) {
            Toast.makeText(mSpeechRecognizerManager.getmContext(),"You said:"+text, Toast.LENGTH_SHORT).show();
            mSpeechRecognizerManager.startDialogFlowRecognition();
            mSpeechRecognizerManager.getmPocketSphinxRecognizer().cancel();
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