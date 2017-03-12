package com.bams.android.multispeechapp.ui.ShoppingList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.R;

import java.util.List;

/**
 * Created by bams on 3/11/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<Product> products;
    private int itemLayout;


    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductsAdapter(List<Product> products, int itemLayout) {
        this.products = products;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.text.setText(product.getName());
        holder.itemView.setTag(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.product_name);
        }
    }

    public void add(Product item, int position) {
        products.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Product item) {
        int position = products.indexOf(item);
        products.remove(position);
        notifyItemRemoved(position);
    }

}

