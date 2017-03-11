package com.bams.android.multispeechapp.ui.dashboard;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.AndroidSpeechRepository;
import com.bams.android.multispeechapp.Data.GoogleMachineRepository;
import com.bams.android.multispeechapp.Data.IbmWatsonRepository;
import com.bams.android.multispeechapp.Data.Repository;

/**
 * Created by bams on 3/9/17.
 */

public class DashboardPresenter implements IDashboardPresenter, IDashboardInteractor.Callback {

    private IDashboardView view;
    private IDashboardInteractor interactor;
    private Repository repository;
    private Enum selectedEngine = EngineSpeech.IBM_WATSON;

    public DashboardPresenter(IDashboardView view, IDashboardInteractor interactor, Repository repository) {
        this.view = view;
        this.interactor = interactor;
        this.repository = repository;
        view.setMenuSelectedEngine((EngineSpeech) selectedEngine);
        interactor.setCallback(this);
        interactor.setRepository(repository);
    }

    @Override
    public void onOpenModalAdd() {

    }

    @Override
    public void onAddProduct() {

    }

    @Override
    public void changeSpeechEngine(EngineSpeech engineSpeech) {
        switch (engineSpeech) {
            case ANDROID_SPEECH:
                interactor.setAndroidSpeech(engineSpeech);
                break;
            case IBM_WATSON:
                interactor.setIbmWatson(engineSpeech);
                break;
            case GOOGLE_MACHINE_LEARNING:
                interactor.setGoogleMachineLearning(engineSpeech);
                break;
        }
    }

    @Override
    public void onChangeEngine(EngineSpeech engineSpeech) {
        view.setMenuDisSelectedEngine((EngineSpeech) selectedEngine);
        view.setMenuSelectedEngine(engineSpeech);
        selectedEngine = engineSpeech;
    }
}
