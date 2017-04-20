package com.bams.android.multispeechapp.ui.ShoppingList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.Presenter.IShoppingListPresenter;
import com.bams.android.multispeechapp.Presenter.ShoppingListPresenter;
import com.bams.android.multispeechapp.R;
import com.bams.android.multispeechapp.Adapters.ProductsAdapter;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;


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
    public void setItems(ArrayList<Product> products) {
        listProducts = products;
        // Hide progress bar
        loadingProducts.setVisibility(View.INVISIBLE);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewProducts.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProducts.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        recyclerViewProducts.setAdapter(
                new ProductsAdapter(products, R.layout.product_item_recycler_view,
                        new ProductsAdapter.OnLongClickListener() {
                            @Override public void onItemClick(Product product) {
                                showDialogToBoughtProduct(product);
                            }
                        }));

    }

    @Override public void showProductAdded(Product product) {
        Toast.makeText(this.getContext(), "NEW PRODUCT ADDED - " + product.status, LENGTH_SHORT).show();
    }

    public void showDialogToBoughtProduct(final Product product) {
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                setProductToBought(product);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("You bought this product?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void setProductToBought(Product product) {
        Product newItem = null;
        presenter.deleteProduct(product.status, product.uid);
        // NEW VALUES OF PRODUCT
        newItem = product;
        newItem.setStatus(ProductStatus.BOUGHT.toString());
        newItem.setDateTime(new Date().getTime());
        // ADD NEW PRODUCT
        presenter.addProduct(newItem);
    }

    public ArrayList<Product> getListProducts() {
        return listProducts;
    }
}
