package com.example.udacitybakingapp;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> activityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void whenRecyclerViewItemClickedThenOpenStepActivity() throws InterruptedException {
        Thread.sleep(SECONDS.toMillis(2));
        onView(withId(R.id.rv_mainActivity)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        if (ApplicationProvider.getApplicationContext().getResources().getBoolean(R.bool.isTablet)) {
            intended(hasComponent(DetailTabletActivity.class.getName()));
        }
        else {
            intended(hasComponent(DetailActivity.class.getName()));
        }

    }
}
