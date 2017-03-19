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
import com.bams.android.multispeechapp.Data.ISpeechInteractor;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;
import com.bams.android.multispeechapp.R;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;

import java.util.ArrayList;

/**
 * Created by bams on 3/10/17.
 */

public class AndroidSpeechRepositoryEngineSpeech extends Activity implements RepositoryEngineSpeech {

    private Context context;
    private ISpeechInteractor.Callback callback;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean mIsListening;

    public AndroidSpeechRepositoryEngineSpeech(Context context, final ISpeechInteractor.Callback callback) {
        this.context = context;
        this.callback = callback;
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this.context);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.context.getPackageName());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onEndOfSpeech() {
                super.onEndOfSpeech();
                callback.onEndSpeech();
            }

            @Override
            public void onReadyForSpeech(Bundle params) {
                super.onReadyForSpeech(params);
                callback.onBeginningOfSpeech();
            }

            @Override
            public void onError(int error) {
                super.onError(error);
                callback.onErrorListen(Integer.toString(error));
            }

            @Override
            public void onResults(Bundle results) {
                super.onResults(results);
                ArrayList<String> matches = results
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                callback.onResponseListen(matches.get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                super.onPartialResults(partialResults);
                ArrayList<String> matches = partialResults
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                callback.onPartialResults(matches.get(0));
            }
        });
    }

    @Override
    public void startRecognition() {
        if (isConnected()) {
            if (!mIsListening) {
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            } else {
                mSpeechRecognizer.cancel();
            }
        } else {
            Toast.makeText(this.context, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStopListen() {
        mSpeechRecognizer.cancel();
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