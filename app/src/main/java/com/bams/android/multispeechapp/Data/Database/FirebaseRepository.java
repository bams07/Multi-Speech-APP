package com.bams.android.multispeechapp.Data.Database;

import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Data.IShoppingListInteractor;
import com.bams.android.multispeechapp.Data.Repository.RepositoryDatabase;
import com.bams.android.multispeechapp.Domain.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bams on 3/11/17.
 */

public class FirebaseRepository implements RepositoryDatabase {

    private final String LIST = "products";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Product> items;

    public FirebaseRepository() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference(LIST);
    }


    @Override
    public void add(Product item) {
        mReference.push().setValue(item);
    }

    @Override
    public void get(final IShoppingListInteractor.Callback callback) {
        items = new ArrayList<Product>();
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Set each element as product class
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    items.add(productSnapshot.getValue(Product.class));
                }
                // Callback
                callback.onGetItems(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

