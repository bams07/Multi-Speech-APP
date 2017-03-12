package com.bams.android.multispeechapp.Presenter;

import android.content.Context;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.IDashboardInteractor;
import com.bams.android.multispeechapp.ui.dashboard.IDashboardView;

/**
 * Created by bams on 3/9/17.
 */

public class DashboardPresenter implements IDashboardPresenter, IDashboardInteractor.Callback {

    private IDashboardView view;
    private IDashboardInteractor interactor;
    private RepositoryEngineSpeech repository;
    private Context context;
    private Enum selectedEngine = EngineSpeech.IBM_WATSON;

    public DashboardPresenter(Context context, IDashboardView view, IDashboardInteractor interactor, RepositoryEngineSpeech repository) {
        this.view = view;
        this.interactor = interactor;
        this.repository = repository;
        this.context = context;
        view.setMenuSelectedEngine((EngineSpeech) selectedEngine);
        interactor.setCallback(this);
        interactor.setContext(context);
        interactor.setRepositoryEngineSpeech(repository);
    }

    @Override
    public void onOpenModalAdd() {

    }

    @Override
    public void onAddProduct(String data) {

    }

    @Override
    public void onListenToAdd() {
        interactor.onListenToAdd();
    }

    @Override
    public void changeSpeechEngine(EngineSpeech engineSpeech) {
        switch (engineSpeech) {
            case ANDROID_SPEECH:
                interactor.setAndroidSpeech(engineSpeech);
                view.toggleMenuEngineSpeech();
                break;
            case IBM_WATSON:
                interactor.setIbmWatson(engineSpeech);
                view.toggleMenuEngineSpeech();
                break;
            case GOOGLE_MACHINE_LEARNING:
                interactor.setGoogleMachineLearning(engineSpeech);
                view.toggleMenuEngineSpeech();
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
