package com.bams.android.multispeechapp.Data.Repository;

import com.bams.android.multispeechapp.Data.ISpeechInteractor;

/**
 * Created by bams on 3/10/17.
 */

public interface IRepositoryEngineSpeech {

    void startRecognition();

    void startTextToSpeech(String toSpeak);

    void onStopListen();

    void onStopTextToSpeech();

    boolean isSpeaking();

    boolean isConnected();

}
