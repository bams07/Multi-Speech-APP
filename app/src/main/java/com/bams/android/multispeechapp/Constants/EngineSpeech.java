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
                return "926a2c30-dff2-4503-8b44-3f3940cf5eac";
            default:
                throw new AssertionError("Unknown operations " + this);
        }
    }

    public String getPassword() {
        switch (this) {
            case IBM_WATSON:
                return "E5QEAoQy2snm";
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
