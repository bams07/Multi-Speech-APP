package com.bams.android.multispeechapp.Data.Repository;

import com.bams.android.multispeechapp.Data.ISpeechInteractor;

/**
 * Created by bams on 3/10/17.
 */

public interface RepositoryEngineSpeech {

    void startRecognition();

    void onStopListen();

    boolean isConnected();

}
