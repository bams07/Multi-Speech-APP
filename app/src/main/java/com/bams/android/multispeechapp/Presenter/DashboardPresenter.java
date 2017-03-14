package com.bams.android.multispeechapp.Presenter;

import android.content.Context;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Data.DashboardInteractor;
import com.bams.android.multispeechapp.Data.Database.FirebaseRepository;
import com.bams.android.multispeechapp.Data.EngineSpeech.AndroidSpeechRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.Repository.RepositoryDatabase;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.IDashboardInteractor;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.ui.dashboard.IDashboardView;

import java.util.Date;

/**
 * Created by bams on 3/9/17.
 */

public class DashboardPresenter implements IDashboardPresenter, IDashboardInteractor.Callback {

    private IDashboardView view;
    private Context context;
    private Enum selectedEngine = EngineSpeech.ANDROID_SPEECH;
    private DashboardInteractor interactor;
    private AndroidSpeechRepositoryEngineSpeech speechRepository;
    private FirebaseRepository databaseRepository;

    public DashboardPresenter(Context context, IDashboardView view) {
        this.view = view;
        this.context = context;
        speechRepository = new AndroidSpeechRepositoryEngineSpeech(this.context);
        databaseRepository = new FirebaseRepository();
        interactor = new DashboardInteractor(speechRepository, this.context);
    }

    @Override
    public void onResume() {
        view.setMenuSelectedEngine((EngineSpeech) selectedEngine);
    }

    @Override
    public void addProduct(String data) {
        Product item = new Product(data, data, data, new Date(), "PENDENT");
        this.databaseRepository.add(item);
        this.onAddProduct();
    }

    @Override
    public void onListenToAdd() {
        interactor.onListenToAdd();
    }

    @Override
    public void changeSpeechEngine(EngineSpeech engineSpeech) {
        switch (engineSpeech) {
            case ANDROID_SPEECH:
                interactor.setAndroidSpeech(engineSpeech, this);
                view.toggleMenuEngineSpeech();
                break;
            case IBM_WATSON:
                interactor.setIbmWatson(engineSpeech, this);
                view.toggleMenuEngineSpeech();
                break;
            case GOOGLE_MACHINE_LEARNING:
                interactor.setGoogleMachineLearning(engineSpeech, this);
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

    @Override
    public void onAddProduct() {
        view.showProductAdded();
    }
}
