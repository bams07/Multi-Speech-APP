package com.bams.android.multispeechapp.ui.ShoppingList;

import com.bams.android.multispeechapp.Domain.Product;

import java.util.List;

/**
 * Created by bams on 3/12/17.
 */

public interface IShoppingListView {

    void setItems(List<Product> items);
}
