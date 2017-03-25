package com.bams.android.multispeechapp.Data.Database;

import android.renderscript.Script;
import android.util.Log;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Data.IProductsInteractor;
import com.bams.android.multispeechapp.Data.Repository.RepositoryDatabase;
import com.bams.android.multispeechapp.Domain.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by bams on 3/11/17.
 */

public class FirebaseRepository implements RepositoryDatabase {

    private final String LIST = "products";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ArrayList<Product> items;
    private IProductsInteractor.Callback callback;
    private Query fbQuery;

    public FirebaseRepository(IProductsInteractor.Callback callback) {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        this.callback = callback;
    }


    @Override
    public void add(Product item) {
        mReference.child(item.status).push().setValue(item);
        this.callback.onAddedProduct();
    }

    @Override
    public FirebaseRepository builder() {
        fbQuery = mReference;
        return this;
    }

    @Override
    public void get() {
        fbQuery.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get product values
                        items = new ArrayList<Product>();
                        for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                            items.add(productSnapshot.getValue(Product.class));
                        }
                        // Reverse order
                        Collections.reverse(items);
                        // Callback
                        callback.onGetItems(items);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("ERROR", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    @Override
    public void getSync() {
        fbQuery.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Set each element as product class
                        items = new ArrayList<Product>();
                        for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                            items.add(productSnapshot.getValue(Product.class));
                        }
                        // Reverse order
                        Collections.reverse(items);
                        // Callback
                        callback.onGetItems(items);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("ERROR", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    /**
     * Get products base on status
     *
     * @param status
     * @return
     */
    @Override
    public FirebaseRepository getByStatus(ProductStatus status) {
        items = new ArrayList<Product>();
        fbQuery = mReference.child(status.toString());
        return this;
    }

    /**
     * Get products between dates
     *
     * @param from
     * @param to
     * @return Instance
     */
    @Override
    public FirebaseRepository getByDate(Date from, Date to) {
        fbQuery = fbQuery.orderByChild("dateTime").startAt(from.getTime()).endAt(to.getTime());
        return this;
    }


}

