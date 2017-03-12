package com.bams.android.multispeechapp.Data;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Data.IShoppingListInteractor;
import com.bams.android.multispeechapp.Data.Repository.RepositoryDatabase;
import com.bams.android.multispeechapp.Domain.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bams on 3/12/17.
 */

public class ShoppingListInteractor implements IShoppingListInteractor {

    private Callback callback;
    private RepositoryDatabase repository;
    private List<Product> items;

    @Override
    public void getProducts() {
        this.repository.get(this.callback);
    }

    @Override
    public void setRepository(RepositoryDatabase repository) {
        this.repository = repository;
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
