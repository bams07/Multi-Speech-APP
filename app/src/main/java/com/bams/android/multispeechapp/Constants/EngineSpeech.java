package com.bams.android.multispeechapp.Constants;

/**
 * Created by bams on 3/9/17.
 */

public enum EngineSpeech {
    IBM_WATSON,
    ANDROID_SPEECH,
    HOUNDIFY,
    GOOGLE_MACHINE_LEARNING;


    public String getUsername() {
        switch (this) {
            case IBM_WATSON:
                return "3046f20e-a409-4c4a-8328-8aeeffbb6404";
            default:
                throw new AssertionError("Unknown operations " + this);
        }
    }

    public String getPassword() {
        switch (this) {
            case IBM_WATSON:
                return "0wfTqhiBpZVf";
            default:
                throw new AssertionError("Unknown operations " + this);
        }
    }

    public String getEndpoint() {
        switch (this) {
            case IBM_WATSON:
                return "https://stream.watsonplatform.net/speech-to-text/api";
            default:
                throw new AssertionError("Unknown operations " + this);
        }
    }

    public String getClientID() {
        switch (this) {
            case HOUNDIFY:
                return "_98dPIown1cCNi3VNxJhJg==";
            default:
                throw new AssertionError("Unknown operations " + this);
        }
    }

    public String getClientKey() {
        switch (this) {
            case HOUNDIFY:
                return "LUbeqnfsDQL1XizsL1WegDcqHDJxCtSs57sCOPQpWwd7zJijjj2sjyGEUNCdlrPgt_hl56GtYvzSP5fqxyfK4g==";
            default:
                throw new AssertionError("Unknown operations " + this);
        }
    }

}
