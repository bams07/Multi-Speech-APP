package com.bams.android.multispeechapp.ui.Dashboard;

import android.graphics.Color;
import android.view.MenuItem;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Constants.SpeechStatus;
import com.bams.android.multispeechapp.Domain.Product;

/**
 * Created by bams on 3/9/17.
 */

public interface IDashboardView {

    void toggleMenuEngineSpeech();

    void setMenuSelectedEngine(EngineSpeech engineSpeech);

    void setMenuDisSelectedEngine(EngineSpeech engineSpeech);

    void showProductAdded(Product product);

    void setOnPartialResults(String message);

    void setSpeechStatus(SpeechStatus status);

    void setOnErrorListen(String error);

    void setProductToAccept(String data);

    void setProductToBought(String data);

    void setProgressAsStopped();

    void closeDialogListening();

    void setMenuItemColor(int color, MenuItem item);

    void toggleMenuItem(MenuItem item, boolean visible);

}
