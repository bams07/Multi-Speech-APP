package com.bams.android.multispeechapp.Data.Repository;

/**
 * Created by bams on 3/10/17.
 */

public interface RepositoryEngineSpeech {

    void startRecognition();

    void onError(String error);

    void onResponse(String data);

    boolean isConnected();

}
