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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bams on 3/9/17.
 */

public class DashboardPresenter
        implements IDashboardPresenter, ISpeechInteractor.Callback, IProductsInteractor.Callback {

    private IDashboardView view;
    private Context context;
    private EngineSpeech selectedEngine = EngineSpeech.ANDROID_SPEECH;
    private SpeechInteractor speechInteractor;
    private ProductsInteractor productsInteractor;

    public DashboardPresenter(Context context, IDashboardView view) {
        this.view = view;
        this.context = context;
        productsInteractor = new ProductsInteractor(new FirebaseRepository(this));
        speechInteractor =
                new SpeechInteractor(new AndroidSpeechRepositoryEngineSpeech(this.context, this),
                        this.context);
    }

    @Override
    public void onResume() {
        view.setMenuSelectedEngine(selectedEngine);
    }

    @Override
    public void addProduct(Product product) {
        productsInteractor.addProduct(product);
    }

    @Override
    public void deleteProduct(String status, String uid) {
        productsInteractor.deleteProduct(status, uid);
    }

    @Override
    public void speechProduct(String toSpeak) {
        this.speechInteractor.speechText(toSpeak);
    }

    @Override
    public void onListenToAdd() {
        speechInteractor.onListenToAdd("TO_ADD");
    }

    @Override public void onListenToBought() {
        speechInteractor.onListenToBought("TO_BOUGHT");
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
    public void onStopTextToSpeech() {
        speechInteractor.onStopTextToSpeech();
    }

    @Override
    public boolean isSpeaking() {
        return speechInteractor.isSpeaking();
    }


    /**
     * LISTEN CALLBACKS
     */

    @Override
    public void onChangeEngine(EngineSpeech engineSpeech) {
        view.setMenuDisSelectedEngine(selectedEngine);
        view.setMenuSelectedEngine(engineSpeech);
        selectedEngine = engineSpeech;
    }

    @Override
    public void onResponseListen(String data, String TAG) {
        switch (TAG) {
            case "TO_ADD":
                view.setProductToAccept(data);
                break;
            case "TO_BOUGHT":
                view.setProductToBought(data);
                break;
        }
    }

    @Override
    public void onBeginningOfSpeech(String TAG) {
        switch (TAG) {
            case "TO_ADD":
                view.setSpeechStatus(SpeechStatus.LISTENING);
                break;
        }
    }

    @Override
    public void onErrorListen(String error, String TAG) {
        switch (TAG) {
            case "TO_ADD":
                view.setOnErrorListen(error);
                break;
        }
    }

    @Override
    public void onPartialResults(String message, String TAG) {
        switch (TAG) {
            case "TO_ADD":
                view.setOnPartialResults(message);
                break;
        }
    }

    @Override
    public void onEndSpeech(String TAG) {
        switch (TAG) {
            case "TO_ADD":
                view.setSpeechStatus(SpeechStatus.STOPPED);
                break;
        }
    }

    @Override
    public EngineSpeech getEngineSpeech(){
        return selectedEngine;
    }


    /**
     * PRODUCTS CALLBACKS
     */

    @Override
    public void onAddedProduct(Product product) {
        view.showProductAdded(product);
        view.closeDialogListening();
    }

    @Override
    public void onGetItems(ArrayList<Product> items) {

    }

    @Override public void onBoughtProduct() {

    }
}
