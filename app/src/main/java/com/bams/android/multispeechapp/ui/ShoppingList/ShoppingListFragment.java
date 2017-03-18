package com.bams.android.multispeechapp.ui.ShoppingList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bams.android.multispeechapp.Data.Database.FirebaseRepository;
import com.bams.android.multispeechapp.Data.ProductsInteractor;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.Presenter.IShoppingListPresenter;
import com.bams.android.multispeechapp.Presenter.ShoppingListPresenter;
import com.bams.android.multispeechapp.R;
import com.bams.android.multispeechapp.ui.ShoppingList.recycler.ProductsAdapter;

import java.util.List;

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

    //    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
    public ShoppingListFragment() {
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ShoppingListFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ShoppingListFragment newInstance(String param1, String param2) {
//        ShoppingListFragment fragment = new ShoppingListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

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
    public void setItems(List<Product> items) {
        // Hide progress bar
        loadingProducts.setVisibility(View.INVISIBLE);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewProducts.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerViewProducts.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        recyclerViewProducts.setAdapter(new ProductsAdapter(items, R.layout.product_item_recycler_view));
    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
