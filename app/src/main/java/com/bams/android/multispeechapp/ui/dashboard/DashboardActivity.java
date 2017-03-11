package com.bams.android.multispeechapp.ui.dashboard;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bams.android.multispeechapp.Data.AndroidSpeechRepository;
import com.bams.android.multispeechapp.Data.IbmWatsonRepository;
import com.bams.android.multispeechapp.Data.Repository;
import com.bams.android.multispeechapp.R;
import com.bams.android.multispeechapp.ui.Reports.ReportsFragment;
import com.bams.android.multispeechapp.ui.ShoppingList.ShoppingListFragment;
import com.bams.android.multispeechapp.Constants.EngineSpeech;
import com.bams.android.multispeechapp.Constants.Fragment;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

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

    private IDashboardPresenter presenter;
    private final int DISELECTED_COLOR = Color.WHITE;
    private final int SELECTED_COLOR = Color.YELLOW;

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

        navView.setNavigationItemSelectedListener(this);
        presenter = new DashboardPresenter(this, new DashboardInteractor(), new IbmWatsonRepository());

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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

    @OnClick({R.id.menu_btn_watson, R.id.menu_btn_google_machine, R.id.menu_btn_android_speech, R.id.menu_speech_engines})
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
            case R.id.menu_speech_engines:
                toggleMenuEngineSpeech();
                break;
        }
    }

    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (fragment) {
            case SHOPPING_LIST_FRAGMENT:
                fragmentManager.beginTransaction().replace(R.id.content_main, new ShoppingListFragment()).commit();
                break;
            case REPORTS_FRAGMENT:
                fragmentManager.beginTransaction().replace(R.id.content_main, new ReportsFragment()).commit();
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
        }
    }
}
