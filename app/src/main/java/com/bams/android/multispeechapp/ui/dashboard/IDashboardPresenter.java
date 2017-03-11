package com.bams.android.multispeechapp.ui.dashboard;

import com.bams.android.multispeechapp.Constants.EngineSpeech;

/**
 * Created by bams on 3/9/17.
 */

public interface IDashboardPresenter {

    void onOpenModalAdd();

    void onAddProduct();

    void changeSpeechEngine(EngineSpeech engineSpeech);
}
