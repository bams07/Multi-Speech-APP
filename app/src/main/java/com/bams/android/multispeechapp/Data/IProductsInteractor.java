package com.bams.android.multispeechapp.Data;

import android.telecom.Call;

import com.bams.android.multispeechapp.Data.Repository.RepositoryDatabase;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;
import com.bams.android.multispeechapp.Domain.Product;

import java.util.List;

/**
 * Created by bams on 3/12/17.
 */

public interface IProductsInteractor {

    void getProducts(Callback callback);

    void addProduct(Product item, Callback callback);

    interface Callback {
        void onAddedProduct();

        void onGetItems(List<Product> items);
    }

}
