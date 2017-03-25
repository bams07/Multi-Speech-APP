package com.bams.android.multispeechapp.Data;

import android.telecom.Call;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Data.Database.FirebaseRepository;
import com.bams.android.multispeechapp.Data.Repository.RepositoryDatabase;
import com.bams.android.multispeechapp.Data.Repository.RepositoryEngineSpeech;
import com.bams.android.multispeechapp.Domain.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bams on 3/12/17.
 */

public interface IProductsInteractor {

    void getProducts();

    void addProduct(Product item);

    interface Callback {
        void onAddedProduct();

        void onGetItems(ArrayList<Product> items);
    }

}
