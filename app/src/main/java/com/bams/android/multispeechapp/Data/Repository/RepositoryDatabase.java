package com.bams.android.multispeechapp.Data.Repository;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Data.Database.FirebaseRepository;
import com.bams.android.multispeechapp.Data.IProductsInteractor;
import com.bams.android.multispeechapp.Domain.Product;

import java.util.Date;

/**
 * Created by bams on 3/10/17.
 */

public interface RepositoryDatabase {

    void add(Product item);

    void get();

    void getSync();

    RepositoryDatabase builder();

    RepositoryDatabase getByStatus(ProductStatus status);

    RepositoryDatabase getByDate(Date from, Date to);

}
