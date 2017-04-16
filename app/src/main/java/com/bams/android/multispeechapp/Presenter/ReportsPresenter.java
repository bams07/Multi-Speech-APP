package com.bams.android.multispeechapp.Presenter;

import android.content.Context;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Data.Database.FirebaseRepository;
import com.bams.android.multispeechapp.Data.IProductsInteractor;
import com.bams.android.multispeechapp.Data.ProductsInteractor;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.ui.Reports.IReportsView;
import com.bams.android.multispeechapp.ui.ShoppingList.IShoppingListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bams on 3/12/17.
 */

public class ReportsPresenter implements IReportsPresenter, IProductsInteractor.Callback {

    private IReportsView view;
    private ProductsInteractor productsInteractor;
    private FirebaseRepository repository;
    private Context context;

    public ReportsPresenter(Context context, IReportsView view) {
        this.view = view;
        this.context = context;
        this.repository = new FirebaseRepository(this);
        this.productsInteractor = new ProductsInteractor(this.repository);
    }

    @Override
    public void onResume() {
        productsInteractor.getProducts();
    }

    @Override public void getProducts(Date dateFrom, Date dateTo, ProductStatus status) {
        productsInteractor.getProducts(dateFrom, dateTo, status);
    }

    @Override
    public void onAddedProduct() {

    }

    @Override
    public void onGetItems(ArrayList<Product> items) {
        view.setItems(items);
    }
}
