package com.bams.android.multispeechapp.Presenter;

import android.content.Context;

import com.bams.android.multispeechapp.Data.IShoppingListInteractor;
import com.bams.android.multispeechapp.Data.Repository.RepositoryDatabase;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.Presenter.IShoppingListPresenter;
import com.bams.android.multispeechapp.ui.ShoppingList.IShoppingListView;

import java.util.List;

/**
 * Created by bams on 3/12/17.
 */

public class ShoppingListPresenter implements IShoppingListPresenter, IShoppingListInteractor.Callback {

    private IShoppingListView view;
    private IShoppingListInteractor interactor;
    private RepositoryDatabase repository;
    private Context context;

    public ShoppingListPresenter(Context context, IShoppingListView view, IShoppingListInteractor interactor, RepositoryDatabase repository) {
        this.view = view;
        this.interactor = interactor;
        this.repository = repository;
        this.context = context;
        this.interactor.setCallback(this);
        this.interactor.setRepository(repository);
    }


    @Override
    public void onResume() {
        interactor.getProducts();

    }

    @Override
    public void onGetItems(List<Product> items) {
        view.setItems(items);
    }
}
