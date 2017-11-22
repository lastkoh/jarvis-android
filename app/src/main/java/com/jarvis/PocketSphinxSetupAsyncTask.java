package com.jarvis;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

/**
 * Created by admin on 22-Nov-17.
 */

public class PocketSphinxSetupAsyncTask extends AsyncTask<Void, Void, Exception> {
    private static final String TAG = PocketSphinxSetupAsyncTask.class.getSimpleName();
    private final SpeechRecognizerManager mSpeechRecognizerManager;

    public PocketSphinxSetupAsyncTask(SpeechRecognizerManager mSpeechRecognizerManager){
        this.mSpeechRecognizerManager = mSpeechRecognizerManager;
    }

    @Override
    protected Exception doInBackground(Void... params) {
        try {
            Assets assets = new Assets(this.mSpeechRecognizerManager.getmContext());

            //Performs the synchronization of assets in the application and external storage
            File assetDir = assets.syncAssets();


            //Creates a new speech recognizer builder with default configuration
            SpeechRecognizerSetup speechRecognizerSetup = SpeechRecognizerSetup.defaultSetup();

            speechRecognizerSetup.setAcousticModel(new File(assetDir, "en-us-ptm"));
            speechRecognizerSetup.setDictionary(new File(assetDir, "cmudict-en-us.dict"));

            // Threshold to tune for keyphrase to balance between false alarms and misses
            speechRecognizerSetup .setKeywordThreshold(1e-45f);

            //Creates a new SpeechRecognizer object based on previous set up.
            mSpeechRecognizerManager.setmPocketSphinxRecognizer(speechRecognizerSetup.getRecognizer());

            // Create keyword-activation search.
            mSpeechRecognizerManager.getmPocketSphinxRecognizer().addKeyphraseSearch(
                    SpeechRecognizerManager.KWS_SEARCH,
                    SpeechRecognizerManager.KEYPHRASE);

            mSpeechRecognizerManager.getmPocketSphinxRecognizer()
                    .addListener(new PocketSphinxRecognitionListener(mSpeechRecognizerManager));
        } catch (IOException e) {
            return e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Exception result) {
        if (result != null) {
            Log.i(TAG,result.getMessage());
            Toast.makeText(mSpeechRecognizerManager.getmContext(), "Failed to start pocketSphinxRecognizer ", Toast.LENGTH_SHORT).show();
        } else {
            mSpeechRecognizerManager.restartSearch(SpeechRecognizerManager.KWS_SEARCH);
        }
    }
}
