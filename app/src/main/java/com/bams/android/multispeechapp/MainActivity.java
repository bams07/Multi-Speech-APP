package com.bams.android.multispeechapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.menu_btn_watson)
    FloatingActionButton menuBtnWatson;
    @BindView(R.id.menu_btn_google_machine)
    FloatingActionButton menuBtnGoogleMachine;
    @BindView(R.id.menu_btn_android_speech)
    FloatingActionButton menuBtnAndroidSpeech;
    @BindView(R.id.menu_speech_engines)
    FloatingActionMenu menuSpeechEngines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @OnClick({R.id.menu_btn_watson, R.id.menu_btn_google_machine, R.id.menu_btn_android_speech, R.id.menu_speech_engines})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_btn_watson:
                break;
            case R.id.menu_btn_google_machine:
                break;
            case R.id.menu_btn_android_speech:
                break;
            case R.id.menu_speech_engines:
                menuSpeechEngines.toggle(true);
                break;
        }
    }
}
