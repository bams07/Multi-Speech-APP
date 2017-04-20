package com.bams.android.multispeechapp.Presenter;

import com.bams.android.multispeechapp.Domain.Product;

/**
 * Created by bams on 3/12/17.
 */

public interface IShoppingListPresenter {

    void onResume();

    void addProduct(Product product);

    void deleteProduct(String status, String uid);

}
