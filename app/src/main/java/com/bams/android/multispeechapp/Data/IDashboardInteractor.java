package com.bams.android.multispeechapp.Data;

import android.content.Context;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;

/**
 * Created by bams on 3/9/17.
 */
public interface IDashboardInteractor {

    void setIbmWatson(EngineSpeech engineSpeech, DashboardInteractor.Callback callback);

    void setAndroidSpeech(EngineSpeech engineSpeech, DashboardInteractor.Callback callback);

    void setGoogleMachineLearning(EngineSpeech engineSpeech, DashboardInteractor.Callback callback);

    void onListenToAdd();

    interface Callback {
        void onChangeEngine(EngineSpeech engineSpeech);

        void onAddProduct();

    }



}
