package com.jarvis.voice;

import ai.api.model.AIResponse;

/**
 * Created by admin on 26-Nov-17.
 */

public interface VoiceManagerListener {
    void onPocketSphinxSetupCompleted(Exception result);
    void onDiaglowFlowApiCompleted(AIResponse response);
}
