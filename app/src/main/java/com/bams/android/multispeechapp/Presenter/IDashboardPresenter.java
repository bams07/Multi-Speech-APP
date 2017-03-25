package com.bams.android.multispeechapp.Presenter;

import com.bams.android.multispeechapp.Constants.EngineSpeech;

/**
 * Created by bams on 3/9/17.
 */

public interface IDashboardPresenter {

    void onResume();

    void addProduct(String data);

    void speechProduct(String toSpeak);

    void onListenToAdd();

    void changeSpeechEngine(EngineSpeech engineSpeech);

    void onStopListen();

    void onStopTextToSpeech();

    boolean isSpeaking();
}
