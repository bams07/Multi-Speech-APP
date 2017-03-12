package com.bams.android.multispeechapp.Data;

import android.content.Context;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;

/**
 * Created by bams on 3/9/17.
 */
public interface IDashboardInteractor {

    void setRepositoryEngineSpeech(RepositoryEngineSpeech repositoryEngineSpeech);

    void setCallback(Callback callback);

    void setIbmWatson(EngineSpeech engineSpeech);

    void setAndroidSpeech(EngineSpeech engineSpeech);

    void setGoogleMachineLearning(EngineSpeech engineSpeech);

    void setContext(Context context);

    void onListenToAdd();

    interface Callback {
        void onChangeEngine(EngineSpeech engineSpeech);
    }



}
