package com.bams.android.multispeechapp.Presenter;

import android.content.Context;

import com.bams.android.multispeechapp.Data.Database.FirebaseRepository;
import com.bams.android.multispeechapp.Data.IProductsInteractor;
import com.bams.android.multispeechapp.Data.ProductsInteractor;
import com.bams.android.multispeechapp.Data.Repository.RepositoryDatabase;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.ui.ShoppingList.IShoppingListView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

/**
 * Created by bams on 3/12/17.
 */

public class ShoppingListPresenter implements IShoppingListPresenter, IProductsInteractor.Callback {

    private IShoppingListView view;
    private ProductsInteractor productsInteractor;
    private FirebaseRepository repository;
    private Context context;

    public ShoppingListPresenter(Context context, IShoppingListView view) {
        this.view = view;
        this.context = context;
        this.repository = new FirebaseRepository();
        this.productsInteractor = new ProductsInteractor(this.repository);
    }

    @Override
    public void onResume() {
        productsInteractor.getProducts(this);
    }

    @Override
    public void onAddedProduct() {

    }

    @Override
    public void onGetItems(List<Product> items) {
        view.setItems(items);
    }
}
