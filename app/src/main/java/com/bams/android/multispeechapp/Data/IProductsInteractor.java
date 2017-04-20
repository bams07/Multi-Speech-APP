package com.bams.android.multispeechapp.Data;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Domain.Product;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bams on 3/12/17.
 */

public interface IProductsInteractor {

    void getProducts();

    void getProducts(Date dateFrom, Date dateTo, ProductStatus status);

    void deleteProduct(String status, String uid);

    void addProduct(Product item);

    interface Callback {
        void onAddedProduct(Product product);

        void onGetItems(ArrayList<Product> items);

        void onBoughtProduct();
    }

}
