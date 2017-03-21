package com.bams.android.multispeechapp.Presenter;

import android.content.Context;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Constants.SpeechStatus;
import com.bams.android.multispeechapp.Data.SpeechInteractor;
import com.bams.android.multispeechapp.Data.Database.FirebaseRepository;
import com.bams.android.multispeechapp.Data.EngineSpeech.AndroidSpeechRepositoryEngineSpeech;
import com.bams.android.multispeechapp.Data.IProductsInteractor;
import com.bams.android.multispeechapp.Data.ProductsInteractor;
import com.bams.android.multispeechapp.Data.ISpeechInteractor;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.ui.Dashboard.IDashboardView;

import java.util.Date;
import java.util.List;

/**
 * Created by bams on 3/9/17.
 */

public class DashboardPresenter implements IDashboardPresenter, ISpeechInteractor.Callback, IProductsInteractor.Callback {

    private IDashboardView view;
    private Context context;
    private Enum selectedEngine = EngineSpeech.ANDROID_SPEECH;
    private SpeechInteractor speechInteractor;
    private ProductsInteractor productsInteractor;
    private AndroidSpeechRepositoryEngineSpeech speechRepository;
    private FirebaseRepository databaseRepository;

    public DashboardPresenter(Context context, IDashboardView view) {
        this.view = view;
        this.context = context;
        speechRepository = new AndroidSpeechRepositoryEngineSpeech(this.context, this);
        databaseRepository = new FirebaseRepository(this);
        productsInteractor = new ProductsInteractor(databaseRepository);
        speechInteractor = new SpeechInteractor(speechRepository, this.context);
    }

    @Override
    public void onResume() {
        view.setMenuSelectedEngine((EngineSpeech) selectedEngine);
    }

    @Override
    public void addProduct(String data) {
        Product item = new Product(data, "", "", new Date().getTime(), ProductStatus.PENDENT.toString());
        productsInteractor.addProduct(item);
    }

    @Override
    public void onListenToAdd() {
        speechInteractor.onListenToAdd();
    }

    @Override
    public void changeSpeechEngine(EngineSpeech engineSpeech) {
        switch (engineSpeech) {
            case ANDROID_SPEECH:
                speechInteractor.setAndroidSpeech(engineSpeech, this);
                view.toggleMenuEngineSpeech();
                break;
            case IBM_WATSON:
                speechInteractor.setIbmWatson(engineSpeech, this);
                view.toggleMenuEngineSpeech();
                break;
            case GOOGLE_MACHINE_LEARNING:
                speechInteractor.setGoogleMachineLearning(engineSpeech, this);
                view.toggleMenuEngineSpeech();
                break;
            case HOUNDIFY:
                speechInteractor.setHoundify(engineSpeech, this);
                view.toggleMenuEngineSpeech();
                break;
        }
    }

    @Override
    public void onStopListen() {
        speechInteractor.onStopListen();
    }

    @Override
    public void onChangeEngine(EngineSpeech engineSpeech) {
        view.setMenuDisSelectedEngine((EngineSpeech) selectedEngine);
        view.setMenuSelectedEngine(engineSpeech);
        selectedEngine = engineSpeech;
    }

    @Override
    public void onResponseListen(String data) {
        view.setProductToAccept(data);
    }

    @Override
    public void onBeginningOfSpeech() {
        view.setSpeechStatus(SpeechStatus.LISTENING);
    }

    @Override
    public void onErrorListen(String error) {
        view.setOnErrorListen(error);
    }

    @Override
    public void onPartialResults(String message) {
        view.setOnPartialResults(message);
    }

    @Override
    public void onEndSpeech() {
        view.setSpeechStatus(SpeechStatus.STOPPED);
    }

    @Override
    public void onAddedProduct() {
        view.showProductAdded();
        view.closeDialogListening();
    }

    @Override
    public void onGetItems(List<Product> items) {

    }
}
