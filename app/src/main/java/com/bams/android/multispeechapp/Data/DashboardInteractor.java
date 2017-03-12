package com.bams.android.multispeechapp.Data;

import android.content.Context;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.EngineSpeech.AndroidSpeechRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.EngineSpeech.GoogleMachineRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.EngineSpeech.IbmWatsonRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.IDashboardInteractor;

/**
 * Created by bams on 3/9/17.
 */

public class DashboardInteractor implements IDashboardInteractor {

    private RepositoryEngineSpeech repository;
    private IDashboardInteractor.Callback callback;
    private Context context;

    @Override
    public void setRepositoryEngineSpeech(RepositoryEngineSpeech repository) {
        this.repository = repository;
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void setIbmWatson(EngineSpeech engineSpeech) {
        this.callback.onChangeEngine(engineSpeech);
        this.repository = new IbmWatsonRepositoryEngineSpeech(this.context);
    }

    @Override
    public void setAndroidSpeech(EngineSpeech engineSpeech) {
        this.callback.onChangeEngine(engineSpeech);
        this.repository = new AndroidSpeechRepositoryEngineSpeech(this.context);
    }

    @Override
    public void setGoogleMachineLearning(EngineSpeech engineSpeech) {
        this.callback.onChangeEngine(engineSpeech);
        this.repository = new GoogleMachineRepositoryEngineSpeech(this.context);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onListenToAdd() {
        this.repository.startRecognition();
    }
}
