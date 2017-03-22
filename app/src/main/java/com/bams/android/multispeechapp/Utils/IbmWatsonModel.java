package com.bams.android.multispeechapp.Utils;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Customization;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechModel;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Word;

import java.lang.reflect.Array;

/**
 * Created by bams on 3/21/17.
 */

public  class IbmWatsonModel {

    public static Word[] getWords(){
        Word[] words = {new Word("a box", "1x", "a box")};
        return words;
    }

}
