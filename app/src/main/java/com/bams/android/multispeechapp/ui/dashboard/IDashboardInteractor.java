package com.bams.android.multispeechapp.ui.dashboard;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.Repository;

/**
 * Created by bams on 3/9/17.
 */
public interface IDashboardInteractor {

    void setRepository(Repository repository);

    void setCallback(Callback callback);

    void setIbmWatson(EngineSpeech engineSpeech);

    void setAndroidSpeech(EngineSpeech engineSpeech);

    void setGoogleMachineLearning(EngineSpeech engineSpeech);

    interface Callback {
        void onChangeEngine(EngineSpeech engineSpeech);
    }



}
