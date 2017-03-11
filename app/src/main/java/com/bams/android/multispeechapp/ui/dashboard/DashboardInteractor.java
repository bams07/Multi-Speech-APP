package com.bams.android.multispeechapp.ui.dashboard;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.AndroidSpeechRepository;
import com.bams.android.multispeechapp.Data.GoogleMachineRepository;
import com.bams.android.multispeechapp.Data.IbmWatsonRepository;
import com.bams.android.multispeechapp.Data.Repository;

/**
 * Created by bams on 3/9/17.
 */

public class DashboardInteractor implements IDashboardInteractor {

    public Repository repository;
    public IDashboardInteractor.Callback callback;


    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void setIbmWatson(EngineSpeech engineSpeech) {
        this.callback.onChangeEngine(engineSpeech);
        this.repository = new IbmWatsonRepository();
    }

    @Override
    public void setAndroidSpeech(EngineSpeech engineSpeech) {
        this.callback.onChangeEngine(engineSpeech);
        this.repository = new AndroidSpeechRepository();
    }

    @Override
    public void setGoogleMachineLearning(EngineSpeech engineSpeech) {
        this.callback.onChangeEngine(engineSpeech);
        this.repository = new GoogleMachineRepository();
    }
}
