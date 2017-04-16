package com.bams.android.multispeechapp.Presenter;

import com.bams.android.multispeechapp.Constants.ProductStatus;

import java.util.Date;

/**
 * Created by bams on 3/12/17.
 */

public interface IReportsPresenter {

    void onResume();

    void getProducts(Date dateFrom, Date dateTo, ProductStatus status);

}
