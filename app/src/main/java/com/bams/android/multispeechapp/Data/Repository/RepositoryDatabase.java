package com.bams.android.multispeechapp.Data.Repository;

import com.bams.android.multispeechapp.Data.IShoppingListInteractor;
import com.bams.android.multispeechapp.Domain.Product;

import java.util.List;

/**
 * Created by bams on 3/10/17.
 */

public interface RepositoryDatabase {

    void add(Product item);

    void get(IShoppingListInteractor.Callback callback);

}
