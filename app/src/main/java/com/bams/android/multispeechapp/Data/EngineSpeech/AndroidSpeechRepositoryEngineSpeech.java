package com.bams.android.multispeechapp.Data.EngineSpeech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionListener;

import com.bams.android.multispeechapp.Constants.RequestCodes;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;

import java.util.ArrayList;

/**
 * Created by bams on 3/10/17.
 */

public class AndroidSpeechRepositoryEngineSpeech extends Activity implements RepositoryEngineSpeech {

    private Context context;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean mIsListening = false;
    private String TAG = "";

    public AndroidSpeechRepositoryEngineSpeech(Context context) {
        this.context = context;
    }

    @Override
    public void startRecognition() {
        if (isConnected()) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            ((Activity) this.context).startActivityForResult(intent, RequestCodes.ANDROID_SPEECH_CODE);

        } else {
            Toast.makeText(this.context, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onResponse(String data) {

    }

    /**
     * Check connection
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(this.context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        return net != null && net.isAvailable() && net.isConnected();
    }

}