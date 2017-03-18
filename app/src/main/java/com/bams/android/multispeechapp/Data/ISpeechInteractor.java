package com.bams.android.multispeechapp.Data;

import com.bams.android.multispeechapp.Constants.EngineSpeech;

/**
 * Created by bams on 3/9/17.
 */
public interface ISpeechInteractor {

    void setIbmWatson(EngineSpeech engineSpeech, SpeechInteractor.Callback callback);

    void setAndroidSpeech(EngineSpeech engineSpeech, SpeechInteractor.Callback callback);

    void setGoogleMachineLearning(EngineSpeech engineSpeech, SpeechInteractor.Callback callback);

    void onListenToAdd(Callback callback);

    interface Callback {
        void onChangeEngine(EngineSpeech engineSpeech);

        void onResponseListen(String data);

        void onErrorListen(String error);

        void onPartialResults(String message);

        void onEndSpeech();

    }



}
