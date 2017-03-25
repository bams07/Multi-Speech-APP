package com.bams.android.multispeechapp.Data.EngineSpeech;

import android.content.Context;

import com.bams.android.multispeechapp.Data.ISpeechInteractor;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;

/**
 * Created by bams on 3/10/17.
 */

public class GoogleMachineRepositoryEngineSpeech implements RepositoryEngineSpeech {

    private Context context;
    private ISpeechInteractor.Callback callback;

    public GoogleMachineRepositoryEngineSpeech(Context context, ISpeechInteractor.Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void startRecognition() {

    }

    @Override
    public void startTextToSpeech(String toSpeak) {

    }

    @Override
    public void onStopListen() {

    }

    @Override
    public void onStopTextToSpeech() {

    }

    @Override
    public boolean isSpeaking() {
        return false;
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
