package com.eventerzgz.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import com.eventerzgz.presenter.BasePresenter;
import com.eventerzgz.view.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jesus_000 on 21/03/2015.
 */
public class SplashScreenActivity extends ActionBarActivity {

    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                boolean tutorialMade = BasePresenter.getTutorialMade(SplashScreenActivity.this);
                Intent mainIntent;
                if(!tutorialMade) {
                    mainIntent = new Intent().setClass(
                            SplashScreenActivity.this, TutorialActivity.class);

                }
                else{
                    mainIntent = new Intent().setClass(
                            SplashScreenActivity.this, ListEventsActivity.class);
                }
                startActivity(mainIntent);
                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}