package com.bams.android.multispeechapp.Data;

import android.content.Context;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.EngineSpeech.AndroidSpeechRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.EngineSpeech.GoogleMachineRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.EngineSpeech.IbmWatsonRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;

/**
 * Created by bams on 3/9/17.
 */

public class SpeechInteractor implements ISpeechInteractor {

    private RepositoryEngineSpeech repository;
    private Context context;

    public SpeechInteractor(RepositoryEngineSpeech repository, Context context) {
        this.repository = repository;
        this.context = context;
    }

    @Override
    public void setIbmWatson(EngineSpeech engineSpeech, SpeechInteractor.Callback callback) {
        callback.onChangeEngine(engineSpeech);
        this.repository = new IbmWatsonRepositoryEngineSpeech(this.context);
    }

    @Override
    public void setAndroidSpeech(EngineSpeech engineSpeech, SpeechInteractor.Callback callback) {
        this.repository = new AndroidSpeechRepositoryEngineSpeech(this.context);
        callback.onChangeEngine(engineSpeech);
    }

    @Override
    public void setGoogleMachineLearning(EngineSpeech engineSpeech, SpeechInteractor.Callback callback) {
        this.repository = new GoogleMachineRepositoryEngineSpeech(this.context);
        callback.onChangeEngine(engineSpeech);
    }

    @Override
    public void onListenToAdd(Callback callback) {
        this.repository.startRecognition(callback);
    }

}
