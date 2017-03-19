package com.bams.android.multispeechapp.Data.EngineSpeech;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.ISpeechInteractor;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bams on 3/10/17.
 */

public class IbmWatsonRepositoryEngineSpeech implements RepositoryEngineSpeech {

    private Context context;
    private ISpeechInteractor.Callback callback;
    private MicrophoneInputStream mInputStream;
    private SpeechToText mSpeechToText = initSpeechToTextService();
    private RecognizeOptions mRecognizeOptions = getRecognizeOptions();
    private BaseRecognizeCallback mBaseCallback;
    private boolean listening = false;

    public IbmWatsonRepositoryEngineSpeech(Context context, ISpeechInteractor.Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void startRecognition() {
        mInputStream = new MicrophoneInputStream(true);

        if (!listening) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mSpeechToText.recognizeUsingWebSocket(
                                mInputStream,
                                mRecognizeOptions,
                                getBaseCallback(callback));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            listening = true;
        } else {
            try {
                mInputStream.close();
                listening = false;
            } catch (Exception e) {
                callback.onErrorListen(e.toString());
                e.printStackTrace();
            }
        }
    }

    public BaseRecognizeCallback getBaseCallback(final ISpeechInteractor.Callback callback) {
        if (mBaseCallback != null) {
            return mBaseCallback;
        }
        return mBaseCallback = new BaseRecognizeCallback() {
            @Override
            public void onTranscription(SpeechResults speechResults) {
                super.onTranscription(speechResults);
                showResponseFromWatsonService(speechResults.getResults(), callback);
            }

            @Override
            public void onConnected() {
                super.onConnected();
                callback.onBeginningOfSpeech();
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                callback.onErrorListen(e.toString());
            }

            @Override
            public void onDisconnected() {
                super.onDisconnected();
                try {
                    mInputStream.close();
                } catch (IOException e) {
                    callback.onErrorListen(e.toString());
                    e.printStackTrace();
                }
                callback.onEndSpeech();
            }
        };

    }


    private SpeechToText initSpeechToTextService() {
        if (mSpeechToText != null) {
            return mSpeechToText;
        }
        SpeechToText service = new SpeechToText();
        String username = EngineSpeech.IBM_WATSON.getUsername();
        String password = EngineSpeech.IBM_WATSON.getPassword();
        service.setEndPoint(EngineSpeech.IBM_WATSON.getEndpoint());
        service.setUsernameAndPassword(username, password);
        return mSpeechToText = service;
    }

    private RecognizeOptions getRecognizeOptions() {
        if (mRecognizeOptions != null) {
            return mRecognizeOptions;
        }
        return mRecognizeOptions = new RecognizeOptions.Builder()
                .continuous(true)
                .contentType(ContentType.OPUS.toString())
                .model(getLanguage())
                .smartFormatting(true)
                .interimResults(true)
                .inactivityTimeout(2000)
                .build();
    }

    private String getLanguage() {
        String selectedItem = "US English";
        switch (selectedItem) {
            case "Modern Standard Arabic":
                selectedItem = "ar-AR_BroadbandModel";
                break;
            case "UK English":
                selectedItem = "en-UK_BroadbandModel";
                break;
            case "US English":
                selectedItem = "en-US_BroadbandModel";
                break;
            case "Spanish":
                selectedItem = "es-ES_BroadbandModel";
                break;
            case "French":
                selectedItem = "fr-FR_BroadbandModel";
                break;
            case "Japanese":
                selectedItem =
                        "ja-JP_BroadbandModel";
                break;
            case "Brazilian":
                selectedItem = "pt-BR_BroadbandModel";
                break;
            case "Mandarin":
                selectedItem = "zh-CN_BroadbandModel";
                break;
        }

        return selectedItem;
    }

    private void showResponseFromWatsonService(List<Transcript> response, ISpeechInteractor.Callback callback) {
        String data = response.get(0).getAlternatives().get(0).getTranscript();
        callback.onPartialResults(data);
        callback.onResponseListen(data);
    }

    @Override
    public void onStopListen() {
        try {
            mInputStream.close();
            listening = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(this.context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        return net != null && net.isAvailable() && net.isConnected();
    }
}
