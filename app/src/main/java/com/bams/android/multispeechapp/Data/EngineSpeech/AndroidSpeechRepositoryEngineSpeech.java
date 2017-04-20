package com.bams.android.multispeechapp.Data.EngineSpeech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;
import android.speech.SpeechRecognizer;

import com.bams.android.multispeechapp.Data.ISpeechInteractor;
import com.bams.android.multispeechapp.Data.Repository.IRepositoryEngineSpeech;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by bams on 3/10/17.
 */

public class AndroidSpeechRepositoryEngineSpeech extends Activity
        implements IRepositoryEngineSpeech {

    private TextToSpeech mTextToSpeech;
    private Context context;
    private ISpeechInteractor.Callback callback;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean mIsListening;
    private boolean mIsPlaying;
    private String TAG;

    public AndroidSpeechRepositoryEngineSpeech(Context context,
            final ISpeechInteractor.Callback callback) {
        this.context = context;
        this.callback = callback;
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this.context);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.context.getPackageName());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");

        mSpeechRecognizer.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onEndOfSpeech() {
                super.onEndOfSpeech();
                callback.onEndSpeech(TAG);
            }

            @Override
            public void onReadyForSpeech(Bundle params) {
                super.onReadyForSpeech(params);
                callback.onBeginningOfSpeech(TAG);
            }

            @Override
            public void onError(int error) {
                super.onError(error);
                callback.onErrorListen(Integer.toString(error), TAG);
            }

            @Override
            public void onResults(Bundle results) {
                super.onResults(results);
                ArrayList<String> matches = results
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                callback.onResponseListen(matches.get(0), TAG);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                super.onPartialResults(partialResults);
                ArrayList<String> matches = partialResults
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                callback.onPartialResults(matches.get(0), TAG);
            }
        });

        mTextToSpeech = new TextToSpeech(this.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });
        mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onDone(String utteranceId) {
                mIsPlaying = false;
            }

            @Override
            public void onError(String utteranceId) {
            }

            @Override
            public void onStart(String utteranceId) {
                mIsPlaying = true;
            }
        });
    }

    @Override
    public void startRecognition(String TAG) {
        this.TAG = TAG;
        if (isConnected()) {
            if (!mIsListening) {
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                mIsListening = true;
            } else {
                mSpeechRecognizer.cancel();
                mIsListening = false;
            }
        } else {
            Toast.makeText(this.context, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void startTextToSpeech(String toSpeak) {
        mTextToSpeech.setLanguage(new Locale("es", "MEX"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTextToSpeech.speak(toSpeak, TextToSpeech.QUEUE_ADD, null, "PRODUCTS");
        } else {
            mTextToSpeech.speak(toSpeak, TextToSpeech.QUEUE_ADD, null);
        }
    }

    @Override
    public void onStopTextToSpeech() {
        mTextToSpeech.stop();
    }

    @Override
    public boolean isSpeaking() {
        return mTextToSpeech.isSpeaking();
    }

    @Override
    public void onStopListen() {
        mSpeechRecognizer.cancel();
        mIsListening = false;
    }

    /**
     * Check connection
     */
    @Override
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(this.context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        return net != null && net.isAvailable() && net.isConnected();
    }

}