package com.indexify.mygoogleanalyticsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyActivity";
    Button btnNext, btnTrackEvent, btnTrackException, btnCrash;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trackMyapp();
        Toast.makeText(this, "oncreate", Toast.LENGTH_SHORT).show();
        // Obtain the shared Tracker instance.


        getControls();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }
    /*@Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Image~" + "MainActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }*/

    private void getControls() {
        btnNext = (Button) findViewById(R.id.btnNext);
        btnCrash = (Button) findViewById(R.id.btnCrash);
        btnTrackEvent = (Button) findViewById(R.id.btnTrackEvent);
        btnTrackException = (Button) findViewById(R.id.btnTrackException);
        btnNext.setOnClickListener(this);
        btnCrash.setOnClickListener(this);
        btnTrackException.setOnClickListener(this);
        btnTrackEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (R.id.btnNext == view.getId()) {
            Intent i = new Intent(MainActivity.this, NextActivity.class);
            startActivity(i);

            Log.i(TAG, "Setting screen name: " + "NextActivity");
            mTracker.setScreenName("NextActivity" + "NextActivity");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Opened")
                    .setAction("Clicked")
                    .build());

        } else if (R.id.btnTrackEvent == view.getId()) {
            Toast.makeText(this, "Event clicked", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "screen name: " + "TrackEvent");
            mTracker.setScreenName("TrackEvent" + "TrackEvent");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("Clicked")
                    .build());

        } else if (R.id.btnTrackException == view.getId()) {
            try {
                List<Long> myList = new ArrayList<>();
                myList.add(Long.valueOf("first"));

                for (int i = 0; i > myList.size(); i++) {
                    System.out.print(myList.size());
                }

            } catch (Exception e) {
                mTracker.setScreenName("TrackException" + "TrackException");
                mTracker.send(new HitBuilders.ExceptionBuilder()
                        .setDescription("Exception" + ":" + e.toString())
                        .setFatal(true)
                        .build());
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else if (R.id.btnCrash == view.getId()) {

            String s = null;
            Toast.makeText(this, "Crashed " + s.length(), Toast.LENGTH_SHORT).show();
            mTracker.setScreenName("Crash" + "Crashed");
            mTracker.send(new HitBuilders.ExceptionBuilder()
                    .setDescription("Crashed on crash button")
                    .setFatal(true)
                    .build());
        }

    }

    public void trackMyapp() {
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        Log.i(TAG, "Setting screen name: " + "MainActivity");
        mTracker.setScreenName("Opened MainActivity" + "MainActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
    }
}
