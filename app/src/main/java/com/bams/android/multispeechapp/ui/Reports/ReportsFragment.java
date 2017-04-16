package com.bams.android.multispeechapp.ui.Reports;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bams.android.multispeechapp.Adapters.ProductsAdapter;
import com.bams.android.multispeechapp.Constants.ProductStatus;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.Helpers.ParseDate;
import com.bams.android.multispeechapp.Presenter.IReportsPresenter;
import com.bams.android.multispeechapp.Presenter.IShoppingListPresenter;
import com.bams.android.multispeechapp.Presenter.ReportsPresenter;
import com.bams.android.multispeechapp.Presenter.ShoppingListPresenter;
import com.bams.android.multispeechapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportsFragment extends Fragment implements IReportsView {

    @BindView(R.id.checkboxPendent) RadioButton checkboxPendent;
    @BindView(R.id.checkboxBought) RadioButton checkboxBought;
    @BindView(R.id.checkboxStatus) RelativeLayout checkboxStatus;
//    @BindView(R.id.ediTextSeachProduct) EditText ediTextSeachProduct;
    @BindView(R.id.textViewFromDate) TextView textViewFromDate;
    @BindView(R.id.textViewToDate) TextView textViewToDate;
    @BindView(R.id.textViewFromTime) TextView textViewFromTime;
    @BindView(R.id.textViewToTime) TextView textViewToTime;
    @BindView(R.id.textViewFromLabel) TextView textViewFromLabel;
    @BindView(R.id.buttonSearchProduct) Button buttonSearchProduct;

    private int dayFrom, monthFrom, yearFrom, hourFrom, minuteFrom;
    private int dayTo, monthTo, yearTo, hourTo, minuteTo;
    private Date dateFrom;
    private Date dateTo;
    private ProductStatus checkedStatus;
    private Calendar calendar;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("EE, MMM d, yyyy");
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("h:mm a");


    Dialog reportsDialog;
    private RecyclerView recyclerViewProducts;
    private ProgressBar loadingProducts;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private IReportsPresenter presenter;
    private ArrayList<Product> listProducts;


    public ReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReportsPresenter(this.getContext(), this);


        /**
         * Calendar
         */

        calendar = Calendar.getInstance();

        // FROM
        dayFrom = calendar.get(Calendar.DAY_OF_MONTH);
        monthFrom = calendar.get(Calendar.MONTH);
        yearFrom = calendar.get(Calendar.YEAR);
        hourFrom = calendar.get(Calendar.HOUR_OF_DAY);
        minuteFrom = calendar.get(Calendar.MINUTE);

        // TO
        dayTo = calendar.get(Calendar.DAY_OF_MONTH);
        monthTo = calendar.get(Calendar.MONTH);
        yearTo = calendar.get(Calendar.YEAR);
        hourTo = calendar.get(Calendar.HOUR_OF_DAY);
        minuteTo = calendar.get(Calendar.MINUTE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override public void onResume() {
        super.onResume();

        textViewFromTime
                .setText(mTimeFormat
                        .format(new Date(ParseDate.getParseYear(yearFrom), monthFrom, dayFrom,
                                hourFrom, minuteFrom)));

        textViewToTime
                .setText(mTimeFormat
                        .format(new Date(ParseDate.getParseYear(yearTo), yearTo, dayTo,
                                hourTo, minuteTo)));

        textViewFromDate.setText(mDateFormat
                .format(new Date(ParseDate.getParseYear(yearFrom), monthFrom, dayFrom)));

        textViewToDate.setText(mDateFormat
                .format(new Date(ParseDate.getParseYear(yearTo), monthTo, dayTo)));
    }

    private void showTimePickerFromDialog(final TextView textView) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourFrom = hourOfDay;
                        minuteFrom = minute;
                        textView.setText(String.format("%1$d:%2$d",
                                hourFrom, minuteFrom));
                    }
                }, hourFrom, minuteFrom, false);
        timePickerDialog.show();

    }

    private void showTimePickerToDialog(final TextView textView) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourTo = hourOfDay;
                        minuteTo = minute;
                        textView.setText(String.format("%1$d:%2$d",
                                hourTo, minuteTo));
                    }
                }, hourTo, minuteTo, false);
        timePickerDialog.show();

    }

    public void showDatePickerFromDialog(final TextView textView) {
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                            int dayOfMonth) {
                        yearFrom = year;
                        monthFrom = monthOfYear;
                        dayFrom = dayOfMonth;
                        textView.setText(String.format("%1$d/%2$d/%3$d",
                                dayOfMonth, (monthOfYear + 1), year));
                    }
                }, yearFrom, monthFrom, dayFrom);
        datePickerDialog.show();
    }

    public void showDatePickerToDialog(final TextView textView) {
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                            int dayOfMonth) {
                        yearTo = year;
                        monthTo = monthOfYear;
                        dayTo = dayOfMonth;
                        textView.setText(String.format("%1$d/%2$d/%3$d",
                                dayOfMonth, (monthOfYear + 1), year));
                    }
                }, yearTo, monthTo, dayTo);
        datePickerDialog.show();
    }

    @OnClick({R.id.textViewFromDate, R.id.textViewToDate, R.id.textViewFromTime,
            R.id.textViewToTime}) public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewFromDate:
                showDatePickerFromDialog(textViewFromDate);
                break;
            case R.id.textViewToDate:
                showDatePickerToDialog(textViewToDate);
                break;
            case R.id.textViewFromTime:
                showTimePickerFromDialog(textViewFromTime);
                break;
            case R.id.textViewToTime:
                showTimePickerToDialog(textViewToTime);
                break;
        }
    }

    @OnClick(R.id.buttonSearchProduct) public void onClick() {
        dateFrom =
                new Date(ParseDate.getParseYear(yearFrom), monthFrom, dayFrom, hourFrom, minuteFrom,
                        0);
        dateTo = new Date(ParseDate.getParseYear(yearTo), monthTo, dayTo, hourTo, minuteTo, 59);
        checkedStatus = checkboxPendent.isChecked() ? ProductStatus.PENDENT : ProductStatus.BOUGHT;

        presenter.getProducts(dateFrom, dateTo, checkedStatus);
    }

    @Override public void setItems(ArrayList<Product> items) {
        listProducts = items;

        if (items.isEmpty()) {
            Toast.makeText(this.getContext(), "SIN RESULTADOS", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        reportsDialog = new Dialog(this.getContext());
        reportsDialog.setContentView(R.layout.fragment_shopping_list);
        reportsDialog.setTitle("REPORTS");
        recyclerViewProducts =
                (RecyclerView) reportsDialog.findViewById(R.id.recycler_view_products);
        loadingProducts = (ProgressBar) reportsDialog.findViewById(R.id.loading_products);


        // Hide progress bar
        loadingProducts.setVisibility(View.INVISIBLE);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewProducts.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProducts.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        recyclerViewProducts
                .setAdapter(new ProductsAdapter(listProducts, R.layout.product_item_recycler_view));

        reportsDialog.show();
    }
}
