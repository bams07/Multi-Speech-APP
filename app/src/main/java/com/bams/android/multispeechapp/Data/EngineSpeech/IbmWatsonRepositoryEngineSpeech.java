package com.bams.android.multispeechapp.Data.EngineSpeech;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bams on 3/10/17.
 */

public class IbmWatsonRepositoryEngineSpeech implements RepositoryEngineSpeech {

    private Context context;
    private MicrophoneInputStream capture;
    private SpeechToText speechService;
    private BaseRecognizeCallback baseCallback;

    public IbmWatsonRepositoryEngineSpeech(Context context) {
        this.context = context;
        speechService = initSpeechToTextService();
    }

    @Override
    public void startRecognition(final ISpeechInteractor.Callback callback) {
        capture = new MicrophoneInputStream(true);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    speechService.recognizeUsingWebSocket(
                            capture,
                            getRecognizeOptions(),
                            getBaseCallback(callback));
                } catch (Exception e) {
                    onError(e.toString());
                }
            }
        }).start();

    }

    public BaseRecognizeCallback getBaseCallback(final ISpeechInteractor.Callback callback) {
        if (baseCallback != null) {
            return baseCallback;
        }
        return baseCallback = new BaseRecognizeCallback() {
            @Override
            public void onTranscription(SpeechResults speechResults) {
                super.onTranscription(speechResults);
                showResponseFromWatsonService(speechResults.getResults(), callback);
            }

            @Override
            public void onConnected() {
                super.onConnected();
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
            }

            @Override
            public void onDisconnected() {
                super.onDisconnected();
            }
        };

    }

    private SpeechToText initSpeechToTextService() {
        SpeechToText service = new SpeechToText();
        String username = EngineSpeech.IBM_WATSON.getUsername();
        String password = EngineSpeech.IBM_WATSON.getPassword();
        service.setEndPoint(EngineSpeech.IBM_WATSON.getEndpoint());
        service.setUsernameAndPassword(username, password);
        return service;
    }

    private RecognizeOptions getRecognizeOptions() {
        return new RecognizeOptions.Builder()
                .contentType(ContentType.OPUS.toString())
                .model(getLanguage())
                .interimResults(true)
                .maxAlternatives(3)
//                .continuous(true)
                .build();
    }

    private String getLanguage() {
//        String selectedItem = spinLang.getSelectedItem().toString();
        String selectedItem = "Spanish";
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

//    private class MicrophoneRecognizeDelegate implements RecognizeCallback {
//        @Override
//        public void onTranscription(SpeechResults speechResults) {
//            final List<Transcript> totalResults = speechResults.getResults();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    showResponseFromWatsonService(totalResults);
//                }
//            });
//        }
//
//        @Override
//        public void onConnected() {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    statusTextView.setText("Listing .....");
//                    recordButton.setText("Stop Recording");
//                }
//            });
//        }
//
//        @Override
//        public void onError(Exception e) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    statusTextView.setText("Failed");
//                }
//            });
//        }
//
//        @Override
//        public void onDisconnected() {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    statusTextView.setText("Done");
//                    resetUIState();
//                }
//            });
//        }
//    }


    private void showResponseFromWatsonService(List<Transcript> response, ISpeechInteractor.Callback callback) {
        final ArrayList<String> finalResultList = new ArrayList<String>();
        String tempTranscript = null;
        Double tempConfidence = null;
        for (int i = 0; i < response.size(); i++) {
            for (int j = 0; j < response.get(i).getAlternatives().size(); j++) {
                tempTranscript = response.get(i).getAlternatives().get(j).getTranscript();
                tempConfidence = response.get(i).getAlternatives().get(j).getConfidence();
                finalResultList.add(tempTranscript + " " + "Confidence: " + tempConfidence);
            }
        }

        callback.onPartialResults(finalResultList.get(0));
    }


    @Override
    public void onError(String error) {

    }

    @Override
    public void onResponse(String data) {
    }

    @Override
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(this.context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        return net != null && net.isAvailable() && net.isConnected();
    }
}
