package com.bams.android.multispeechapp.Presenter;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Domain.Product;

/**
 * Created by bams on 3/9/17.
 */

public interface IDashboardPresenter {

    void onResume();

    EngineSpeech getEngineSpeech();

    void addProduct(Product product);

    void deleteProduct(String status, String uid);

    void speechProduct(String toSpeak);

    void onListenToAdd();

    void onListenToBought();

    void changeSpeechEngine(EngineSpeech engineSpeech);

    void onStopListen();

    void onStopTextToSpeech();

    boolean isSpeaking();
}
