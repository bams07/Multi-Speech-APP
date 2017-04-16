package com.bams.android.multispeechapp.ui.ShoppingList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.Presenter.IShoppingListPresenter;
import com.bams.android.multispeechapp.Presenter.ShoppingListPresenter;
import com.bams.android.multispeechapp.R;
import com.bams.android.multispeechapp.Adapters.ProductsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShoppingListFragment extends Fragment implements IShoppingListView {

    @BindView(R.id.recycler_view_products)
    RecyclerView recyclerViewProducts;
    @BindView(R.id.loading_products)
    ProgressBar loadingProducts;

    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private IShoppingListPresenter presenter;
    private ArrayList<Product> listProducts;

    public ShoppingListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ShoppingListPresenter(this.getContext(), this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

    }

    @Override
    public void setItems(ArrayList<Product> items) {
        listProducts = items;
        // Hide progress bar
        loadingProducts.setVisibility(View.INVISIBLE);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewProducts.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProducts.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        recyclerViewProducts.setAdapter(new ProductsAdapter(items, R.layout.product_item_recycler_view));
    }

    public ArrayList<Product> getListProducts() {
        return listProducts;
    }
}
