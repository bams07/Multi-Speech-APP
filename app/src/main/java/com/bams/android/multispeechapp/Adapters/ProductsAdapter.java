package com.bams.android.multispeechapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by bams on 3/11/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<Product> products;
    private int itemLayout;
    private SimpleDateFormat ft;
    private final OnLongClickListener listener;


    public interface OnLongClickListener {
        void onItemClick(Product product);

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductsAdapter(List<Product> products, int itemLayout, OnLongClickListener listener) {
        this.products = products;
        this.itemLayout = itemLayout;
        this.listener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductsAdapter(List<Product> products, int itemLayout) {
        this.products = products;
        this.itemLayout = itemLayout;
        this.listener = null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        ft = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
        Date date = new Date(product.getDateTime());
        holder.productName.setText(product.getName());
        holder.productStatus.setText(product.getStatus());
        holder.productDate.setText(ft.format(date));
        holder.itemView.setTag(product);
        if(listener != null){
            holder.bind(this.products.get(position), listener);
        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView productStatus;
        public TextView productDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productStatus = (TextView) itemView.findViewById(R.id.product_status);
            productDate = (TextView) itemView.findViewById(R.id.product_date);

        }

        public void bind(final Product product, final OnLongClickListener listener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override public boolean onLongClick(View v) {
                    listener.onItemClick(product);
                    return false;
                }
            });
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

