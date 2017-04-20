package com.bams.android.multispeechapp.Data;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Data.Repository.IRepositoryDatabase;
import com.bams.android.multispeechapp.Domain.Product;

import java.util.Date;
import java.util.List;

/**
 * Created by bams on 3/12/17.
 */

public class ProductsInteractor implements IProductsInteractor {

    private IRepositoryDatabase repository;
    private List<Product> items;

    public ProductsInteractor(IRepositoryDatabase repository) {
        this.repository = repository;
    }

    @Override
    public void getProducts() {
        this.repository.builder().getByStatus(ProductStatus.PENDENT).getSync();
    }

    @Override public void getProducts(Date dateFrom, Date dateTo, ProductStatus status) {
        this.repository.builder().getByStatus(status).getByDate(dateFrom, dateTo).get();

    }

    @Override public void deleteProduct(String status, String uid) {
        this.repository.delete(status, uid);
    }

    @Override
    public void addProduct(Product item) {
        this.repository.add(item);
    }

}
