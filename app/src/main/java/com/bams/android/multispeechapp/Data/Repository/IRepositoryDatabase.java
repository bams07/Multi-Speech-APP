package com.bams.android.multispeechapp.Data.Repository;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Domain.Product;

import java.util.Date;

/**
 * Created by bams on 3/10/17.
 */

public interface IRepositoryDatabase {

    void add(Product item);

    void get();

    void getSync();

    void delete(String status, String uid);

    IRepositoryDatabase builder();

    IRepositoryDatabase getByStatus(ProductStatus status);

    IRepositoryDatabase getByDate(Date from, Date to);

}
