package com.bams.android.multispeechapp.Data.EngineSpeech;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.ISpeechInteractor;
import com.bams.android.multispeechapp.Data.Repository.IRepositoryEngineSpeech;
import com.fasterxml.jackson.databind.JsonNode;
import com.hound.android.sdk.VoiceSearch;
import com.hound.android.sdk.VoiceSearchInfo;
import com.hound.android.sdk.VoiceSearchListener;
import com.hound.android.sdk.VoiceSearchState;
import com.hound.android.sdk.audio.SimpleAudioByteStreamSource;
import com.hound.android.sdk.util.HoundRequestInfoFactory;
import com.hound.core.model.sdk.HoundRequestInfo;
import com.hound.core.model.sdk.HoundResponse;
import com.hound.core.model.sdk.PartialTranscript;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by bams on 3/10/17.
 */

public class HoundifyRepositoryEngineSpeech implements IRepositoryEngineSpeech {

    private Context context;
    private ISpeechInteractor.Callback callback;
    private VoiceSearch mVoiceSearch;
    private LocationManager mLocationManager;
    private JsonNode mLastConversationState;
    private VoiceSearchListener mVoiceListener;
    private String TAG;

    public HoundifyRepositoryEngineSpeech(Context context, ISpeechInteractor.Callback callback) {
        this.context = context;
        this.callback = callback;

        // Initialize Miscellaneous Components
        mLocationManager =
                (LocationManager) this.context.getSystemService(this.context.LOCATION_SERVICE);
        mVoiceListener = getVoiceListener();

    }

    @Override
    public void startRecognition(String TAG) {
        this.TAG = TAG;
        if (isConnected()) {
            // No VoiceSearch is active, start one.
            if (mVoiceSearch == null) {
                startSearch();
            }
            // Else stop the current search
            else {
                // voice search has already started.
                if (mVoiceSearch.getState() == VoiceSearchState.STATE_STARTED) {
                    mVoiceSearch.stopRecording();
                } else {
                    mVoiceSearch.abort();
                }
            }
        } else {
            // Display user message to conect to internet
            Toast.makeText(this.context, "Plese Connect to Internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void startTextToSpeech(String toSpeak) {

    }


    private void startSearch() {
        if (mVoiceSearch != null) {
            return; // We are already searching
        }
        /**
         * Example of using the VoiceSearch.Builder to configure a VoiceSearch object
         * which is then use to run the voice search.
         */
        mVoiceSearch = new VoiceSearch.Builder()
                .setRequestInfo(getHoundRequestInfo())
                .setAudioSource(new SimpleAudioByteStreamSource())
                .setClientId(EngineSpeech.HOUNDIFY.getClientID())     // Client ID for access API
                .setClientKey(EngineSpeech.HOUNDIFY.getClientKey())   // Client KEY for access API
                .setListener(mVoiceListener)
                .build();


        callback.onBeginningOfSpeech(TAG);
        // Toggle the text on our record button to indicate pressing it now will abort the search.
//        recordButton.setText("Stop Recording");

        // Kickoff the search. This will start listening from the microphone and streaming
        // the audio to the Hound server, at the same time, waiting for a response which will be passed
        // back as a result to the voiceListener registered above.
        mVoiceSearch.start();
    }


    /**
     * Helper method called from the startSearch() method below to fill out user information needed
     * in the HoundRequestInfo query object sent to the Hound server.
     */
    private HoundRequestInfo getHoundRequestInfo() {
        final HoundRequestInfo requestInfo = HoundRequestInfoFactory.getDefault(this.context);

        // Client App is responsible for providing a UserId for their users which is meaningful to the client.
        requestInfo.setUserId("ID2");
        // Each request must provide a unique request ID.D
        requestInfo.setRequestId(UUID.randomUUID().toString());
        // Providing the user's location is useful for geographic queries, such as, "Show me restaurants near me".

        //setLocation(requestInfo, locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER));

        // for the first search lastConversationState will be null, this is okay.  However any future
        // searches may return us a conversation state to use.  Add it to the request info when we have one.
        requestInfo.setConversationState(mLastConversationState);

        // set maximum results to show
        requestInfo.setMaxResults(10);
        // set minimun results to show
        requestInfo.setMinResults(5);

        return requestInfo;
    }


    /**
     * Implementation of the VoiceSearchListener interface used for receiving search state
     * information and the final search results.
     */
    private VoiceSearchListener getVoiceListener() {
        if (mVoiceListener != null) {
            return mVoiceListener;
        }

        return mVoiceListener = new VoiceSearchListener() {
            /**
             * Called every time a new partial transcription is received from the Hound server.
             * This is used for providing feedback to the user of the server's interpretation of their query.
             *
             * @param transcript
             */
            @Override
            public void onTranscriptionUpdate(final PartialTranscript transcript) {
                callback.onPartialResults(transcript.getPartialTranscript(), TAG);
            }

            /**
             * Called when the Hound Server fully response
             *
             * @param response
             * @param info
             */
            @Override
            public void onResponse(final HoundResponse response, final VoiceSearchInfo info) {
                mVoiceSearch = null;
                // Make sure the request succeeded with OK
                if (response.getStatus().equals(HoundResponse.Status.OK)) {
                    if (!response.getResults().isEmpty()) {
                        // Save off the conversation state.  This information will be returned to the server
                        // in the next search. Note that at some point in the future the results CommandResult list
                        // may contain more than one item. For now it does not, so just grab the first result's
                        // conversation state and use it.
                        mLastConversationState =
                                response.getResults().get(0).getConversationState();
                    }
                    // We put pretty printing JSON on a separate thread as the server JSON can be quite large and will stutter the UI
                    // Not meant to be configuration change proof, this is just a demo
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonResponse;
                            try {
                                jsonResponse = new JSONObject(info.getContentBody());
                            } catch (final JSONException ex) {
                                callback.onErrorListen("Bad JSON\n\n" + response, TAG);
                                jsonResponse = new JSONObject();
                            }
                            final JSONObject finalJson = jsonResponse;
                            try {
                                // Will show the result in the UI List View
                                showResponseFromHoundService(finalJson);
                            } catch (JSONException e) {
                                callback.onErrorListen(e.toString(), TAG);
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    callback.onErrorListen("Request failed with" + response.getErrorMessage(),TAG);
                }

            }

            /**
             * Called if the search fails do to some kind of error situation.
             *
             * @param ex
             * @param info
             */
            @Override
            public void onError(final Exception ex, final VoiceSearchInfo info) {
                mVoiceSearch = null;
                callback.onErrorListen(exceptionToString(ex), TAG);
            }

            /**
             * Called when the recording phase is completed.
             */
            @Override
            public void onRecordingStopped() {
                callback.onEndSpeech(TAG);
            }

            /**
             * Called if the user aborted the search.
             *
             * @param info
             */
            @Override
            public void onAbort(final VoiceSearchInfo info) {
                mVoiceSearch = null;
                callback.onEndSpeech(TAG);
            }
        };

    }

    /**
     * Helper method for converting an Exception to a String with stack trace info.
     */
    private static String exceptionToString(final Exception ex) {
        try {
            final StringWriter sw = new StringWriter(1024);
            final PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.close();
            return sw.toString();
        } catch (final Exception e) {
            return "";
        }
    }


    private void showResponseFromHoundService(JSONObject response) throws JSONException {

        JSONArray jsonResultsArr = response.getJSONArray("AllResults");
        final ArrayList<String> finalResultList = new ArrayList<String>();
        for (int i = 0; i < jsonResultsArr.length(); i++) {
            JSONObject gg = (JSONObject) jsonResultsArr.get(i);
            finalResultList.add(gg.getString("WrittenResponse"));
        }
        callback.onResponseListen(finalResultList.get(0), TAG);
        callback.onPartialResults(finalResultList.get(0), TAG);
    }


    @Override
    public void onStopListen() {
        if (mVoiceSearch != null) {
            mVoiceSearch.stopRecording();
        }
    }

    @Override
    public void onStopTextToSpeech() {

    }

    @Override
    public boolean isSpeaking() {
        return false;
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
