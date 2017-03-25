package com.bams.android.multispeechapp.Data;

import android.content.Context;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.EngineSpeech.AndroidSpeechRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.EngineSpeech.GoogleMachineRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.EngineSpeech.HoundifyRepositoryEngineSpeech;
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
        this.repository = new IbmWatsonRepositoryEngineSpeech(this.context, callback);
    }

    @Override
    public void setAndroidSpeech(EngineSpeech engineSpeech, SpeechInteractor.Callback callback) {
        callback.onChangeEngine(engineSpeech);
        this.repository = new AndroidSpeechRepositoryEngineSpeech(this.context, callback);
    }

    @Override
    public void setGoogleMachineLearning(EngineSpeech engineSpeech, SpeechInteractor.Callback callback) {
        callback.onChangeEngine(engineSpeech);
        this.repository = new GoogleMachineRepositoryEngineSpeech(this.context, callback);
    }

    @Override
    public void setHoundify(EngineSpeech engineSpeech, SpeechInteractor.Callback callback) {
        callback.onChangeEngine(engineSpeech);
        this.repository = new HoundifyRepositoryEngineSpeech(this.context, callback);
    }

    @Override
    public void onListenToAdd() {
        this.repository.startRecognition();
    }

    @Override
    public void onStopListen() {
        this.repository.onStopListen();
    }

    @Override
    public void onStopTextToSpeech() {
        this.repository.onStopTextToSpeech();
    }

    @Override
    public boolean isSpeaking() {
        return this.repository.isSpeaking();
    }

    @Override
    public void speechText(String toSpeak) {
        this.repository.startTextToSpeech(toSpeak);
    }

}
