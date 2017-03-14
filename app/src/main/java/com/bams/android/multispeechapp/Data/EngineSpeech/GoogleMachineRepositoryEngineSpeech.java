package com.bams.android.multispeechapp.Data.EngineSpeech;

import android.content.Context;

import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;

/**
 * Created by bams on 3/10/17.
 */

public class GoogleMachineRepositoryEngineSpeech implements RepositoryEngineSpeech {

    private Context context;

    public GoogleMachineRepositoryEngineSpeech(Context context) {
        this.context = context;
    }

    @Override
    public void startRecognition() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onResponse(String data) {

    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
