package com.bams.android.multispeechapp.ui.dashboard;

import com.bams.android.multispeechapp.Constants.EngineSpeech;

/**
 * Created by bams on 3/9/17.
 */

public interface IDashboardView {

    void toggleMenuEngineSpeech();

    void setMenuSelectedEngine(EngineSpeech engineSpeech);

    void setMenuDisSelectedEngine(EngineSpeech engineSpeech);


}
