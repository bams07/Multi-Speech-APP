package com.bams.android.multispeechapp.ui.Dashboard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Constants.Fragment;
import com.bams.android.multispeechapp.Constants.SpeechStatus;
import com.bams.android.multispeechapp.Domain.Product;
import com.bams.android.multispeechapp.Presenter.DashboardPresenter;
import com.bams.android.multispeechapp.Presenter.IDashboardPresenter;
import com.bams.android.multispeechapp.R;
import com.bams.android.multispeechapp.ui.Reports.ReportsFragment;
import com.bams.android.multispeechapp.ui.ShoppingList.ShoppingListFragment;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity
        implements IDashboardView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.menu_btn_watson)
    FloatingActionButton menuBtnWatson;
    @BindView(R.id.menu_btn_google_machine)
    FloatingActionButton menuBtnGoogleMachine;
    @BindView(R.id.menu_btn_android_speech)
    FloatingActionButton menuBtnAndroidSpeech;
    @BindView(R.id.menu_speech_engines)
    FloatingActionMenu menuSpeechEngines;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @Nullable
    @BindView(R.id.textViewProcess)
    TextView textViewProcess;
    @Nullable
    @BindView(R.id.progressListening)
    ProgressBar progressListening;
    @Nullable
    @BindView(R.id.textViewPartialMessage)
    TextView textViewPartialMessage;
    @Nullable
    @BindView(R.id.dialog_cancel)
    Button dialogCancel;
    @Nullable
    @BindView(R.id.btnDialogAddProduct)
    Button btnDialogAddProduct;
    @BindView(R.id.menu_btn_houndify)
    FloatingActionButton menuBtnHoundify;


    private IDashboardPresenter presenter;
    public FragmentManager fragmentManager = getSupportFragmentManager();
    private final int DISELECTED_COLOR = Color.WHITE;
    private final int SELECTED_COLOR = Color.YELLOW;
    private String productData = null;
    private Dialog dialogListening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        dialogListening = new Dialog(this);

        dialogListening.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                presenter.onStopListen();
            }
        });

        navView.setNavigationItemSelectedListener(this);

        presenter = new DashboardPresenter(this, this);

        changeFragment(Fragment.SHOPPING_LIST_FRAGMENT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add_product:
                openListeningDialog();
                presenter.onListenToAdd();
                return true;
            case R.id.action_listen_list_products:
                if (!presenter.isSpeaking()) {

                    setMenuItemColor(R.color.appYellow, item);
                    listenProducts(item);

                } else {

                    setMenuItemColor(R.color.black, item);
                    presenter.onStopTextToSpeech();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_btn_my_list) {
            changeFragment(Fragment.SHOPPING_LIST_FRAGMENT);

            // Handle the camera action
        } else if (id == R.id.drawer_btn_reports) {
            changeFragment(Fragment.REPORTS_FRAGMENT);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    void listenProducts(MenuItem item) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ShoppingListFragment sListFragment = (ShoppingListFragment)
                            getSupportFragmentManager().findFragmentByTag("SHOPPING_LIST");

                    ArrayList<Product> items = new ArrayList<Product>();

                    if (sListFragment != null) {

                        items = sListFragment.getListProducts();
                        for (Product item : items) {
                            presenter.speechProduct(item.name);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @OnClick({R.id.menu_btn_watson, R.id.menu_btn_google_machine, R.id.menu_btn_android_speech, R.id.menu_btn_houndify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_btn_watson:
                presenter.changeSpeechEngine(EngineSpeech.IBM_WATSON);
                break;
            case R.id.menu_btn_google_machine:
                presenter.changeSpeechEngine(EngineSpeech.GOOGLE_MACHINE_LEARNING);
                break;
            case R.id.menu_btn_android_speech:
                presenter.changeSpeechEngine(EngineSpeech.ANDROID_SPEECH);
                break;
            case R.id.menu_btn_houndify:
                presenter.changeSpeechEngine(EngineSpeech.HOUNDIFY);
                break;
        }
    }

    public void changeFragment(Fragment fragment) {
        switch (fragment) {
            case SHOPPING_LIST_FRAGMENT:
                fragmentManager.beginTransaction().replace(R.id.content_main, new ShoppingListFragment(), "SHOPPING_LIST").commit();
                break;
            case REPORTS_FRAGMENT:
                fragmentManager.beginTransaction().replace(R.id.content_main, new ReportsFragment(), "REPORTS").commit();
                break;
        }
    }

    @Override
    public void toggleMenuEngineSpeech() {
        menuSpeechEngines.toggle(true);
    }

    @Override
    public void setMenuSelectedEngine(EngineSpeech engineSpeech) {
        switch (engineSpeech) {
            case IBM_WATSON:
                menuBtnWatson.setColorNormal(SELECTED_COLOR);
                break;
            case ANDROID_SPEECH:
                menuBtnAndroidSpeech.setColorNormal(SELECTED_COLOR);
                break;
            case GOOGLE_MACHINE_LEARNING:
                menuBtnGoogleMachine.setColorNormal(SELECTED_COLOR);
                break;
            case HOUNDIFY:
                menuBtnHoundify.setColorNormal(SELECTED_COLOR);
                break;
        }
    }

    @Override
    public void setMenuDisSelectedEngine(EngineSpeech engineSpeech) {
        switch (engineSpeech) {
            case IBM_WATSON:
                menuBtnWatson.setColorNormal(DISELECTED_COLOR);
                break;
            case ANDROID_SPEECH:
                menuBtnAndroidSpeech.setColorNormal(DISELECTED_COLOR);
                break;
            case GOOGLE_MACHINE_LEARNING:
                menuBtnGoogleMachine.setColorNormal(DISELECTED_COLOR);
                break;
            case HOUNDIFY:
                menuBtnHoundify.setColorNormal(DISELECTED_COLOR);
                break;
        }
    }

    @Override
    public void showProductAdded() {
        Toast.makeText(this, "NEW PRODUCT ADDED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOnPartialResults(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewPartialMessage.setText(message);
            }
        });
    }

    @Override
    public void setSpeechStatus(final SpeechStatus status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewProcess.setText(status.toString());
            }
        });
    }

    @Override
    public void setOnErrorListen(String error) {

    }

    @Override
    public void setProductToAccept(String data) {
        productData = data;
    }

    @Override
    public void setProgressAsStopped() {
    }

    @Override
    public void closeDialogListening() {
        dialogListening.dismiss();
    }

    @Override
    public void setMenuItemColor(int color, MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void openListeningDialog() {
        dialogListening.setContentView(R.layout.fragment_listening);
        progressListening = ButterKnife.findById(dialogListening, R.id.progressListening);
        textViewPartialMessage = ButterKnife.findById(dialogListening, R.id.textViewPartialMessage);
        dialogCancel = ButterKnife.findById(dialogListening, R.id.dialog_cancel);
        btnDialogAddProduct = ButterKnife.findById(dialogListening, R.id.btnDialogAddProduct);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialogListening();
                presenter.onStopListen();
            }
        });
        btnDialogAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfProductExits(productData)) {
                    dialogListening.hide();
                    showDialogToAddProduct(productData);
                } else {
                    presenter.addProduct(productData);
                }
                presenter.onStopListen();
            }
        });
        textViewProcess = ButterKnife.findById(dialogListening, R.id.textViewProcess);
        dialogListening.show();
    }


    private boolean checkIfProductExits(String checkProduct) {
        ShoppingListFragment sListFragment = (ShoppingListFragment)
                getSupportFragmentManager().findFragmentByTag("SHOPPING_LIST");

        ArrayList<Product> items = new ArrayList<Product>();

        items = sListFragment.getListProducts();

        for (Product item : items) {
            if (item.name.equals(productData)) {
                return true;
            }
        }

        return false;
    }

    private void showDialogToAddProduct(final String productData) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        presenter.addProduct(productData);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format("The product \"%s\" it's already exists, you want to create new one?", productData))
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}
