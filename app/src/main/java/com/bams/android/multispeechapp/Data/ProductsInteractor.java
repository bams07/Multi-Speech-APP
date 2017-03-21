package com.bams.android.multispeechapp.Data;

import android.telecom.Call;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Data.Repository.RepositoryDatabase;
import com.bams.android.multispeechapp.Domain.Product;

import java.util.List;

/**
 * Created by bams on 3/12/17.
 */

public class ProductsInteractor implements IProductsInteractor {

    private RepositoryDatabase repository;
    private List<Product> items;

    public ProductsInteractor(RepositoryDatabase repository) {
        this.repository = repository;
    }

    @Override
    public void getProducts() {
        this.repository.builder().getByStatus(ProductStatus.PENDENT).getSync();
    }

    @Override
    public void addProduct(Product item) {
        this.repository.add(item);
    }

}
