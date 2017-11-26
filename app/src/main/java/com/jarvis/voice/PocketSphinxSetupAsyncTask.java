package com.jarvis.voice;

import android.os.AsyncTask;
import android.util.Log;

import com.jarvis.Constants;

import java.io.File;
import java.io.IOException;

import ai.api.AIServiceException;
import ai.api.model.AIEvent;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

/**
 * Created by admin on 22-Nov-17.
 */

public class PocketSphinxSetupAsyncTask extends AsyncTask<Void, Void, AIResponse> {
    private static final String TAG = PocketSphinxSetupAsyncTask.class.getSimpleName();
    private Exception mException;
    private final VoiceManager mSpeechRecognizerManager;

    public PocketSphinxSetupAsyncTask(VoiceManager mSpeechRecognizerManager){
        this.mSpeechRecognizerManager = mSpeechRecognizerManager;
    }

    @Override
    protected AIResponse doInBackground(Void... params) {
        AIResponse response = null;
        try {
            Assets assets = new Assets(mSpeechRecognizerManager.getmContext());

            //Performs the synchronization of assets in the application and external storage
            File assetDir = assets.syncAssets();


            //Creates a new speech recognizer builder with default configuration
            SpeechRecognizerSetup speechRecognizerSetup = SpeechRecognizerSetup.defaultSetup();

            speechRecognizerSetup.setAcousticModel(new File(assetDir, "en-us-ptm"));
            speechRecognizerSetup.setDictionary(new File(assetDir, "cmudict-en-us.dict"));

            // Threshold to tune for keyphrase to balance between false alarms and misses
            speechRecognizerSetup .setKeywordThreshold(Constants.VOICE.THRESHOLD);

            //Creates a new SpeechRecognizer object based on previous set up.
            mSpeechRecognizerManager.setmPocketSphinxRecognizer(speechRecognizerSetup.getRecognizer());

            // Create keyword-activation search.
            mSpeechRecognizerManager.getmPocketSphinxRecognizer().addKeyphraseSearch(
                    Constants.VOICE.KWS_SEARCH,
                    Constants.VOICE.WAKE_UP_KEYPHRASE);

            mSpeechRecognizerManager.getmPocketSphinxRecognizer()
                    .addListener(new PocketSphinxRecognitionListener(mSpeechRecognizerManager));

            AIRequest aiRequest = new AIRequest();
            aiRequest.setEvent(new AIEvent(Constants.VOICE.EVENT_WELCOME));
            response = mSpeechRecognizerManager.getmAIDataService().request(aiRequest);
        } catch (IOException e) {
            mException = e;
        } catch (AIServiceException e){
            mException = e;
        }
        return response;
    }

    @Override
    protected void onPostExecute(AIResponse response) {
        if (mException != null) {
            Log.i(TAG,mException.getMessage());
        } else if(response != null){
            mSpeechRecognizerManager.pocketSphinxStartListening();
            mSpeechRecognizerManager.speak(response.getResult().getFulfillment().getSpeech());
        }
    }
}
