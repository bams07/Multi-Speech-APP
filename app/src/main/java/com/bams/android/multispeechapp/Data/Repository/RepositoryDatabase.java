package com.bams.android.multispeechapp.Data.Repository;

import com.bams.android.multispeechapp.Data.IProductsInteractor;
import com.bams.android.multispeechapp.Domain.Product;

/**
 * Created by bams on 3/10/17.
 */

public interface RepositoryDatabase {

    void add(Product item);

    void get(IProductsInteractor.Callback callback);

}
