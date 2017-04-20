package com.bams.android.multispeechapp.Presenter;

import android.content.Context;

import com.bams.android.multispeechapp.Data.Database.FirebaseRepository;
import com.bams.android.multispeechapp.Data.IProductsInteractor;
import com.bams.android.multispeechapp.Data.ProductsInteractor;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.ui.ShoppingList.IShoppingListView;

import java.util.ArrayList;

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
        this.repository = new FirebaseRepository(this);
        this.productsInteractor = new ProductsInteractor(this.repository);
    }

    @Override
    public void onResume() {
        productsInteractor.getProducts();
    }

    @Override
    public void addProduct(Product product) {
        productsInteractor.addProduct(product);
    }

    @Override
    public void deleteProduct(String status, String uid) {
        productsInteractor.deleteProduct(status, uid);
    }

    /**
     * PRODUCTS CALLBACKS
     */

    @Override
    public void onAddedProduct(Product product) {
        view.showProductAdded(product);
    }

    @Override
    public void onGetItems(ArrayList<Product> items) {
        view.setItems(items);
    }

    @Override public void onBoughtProduct() {

    }
}
