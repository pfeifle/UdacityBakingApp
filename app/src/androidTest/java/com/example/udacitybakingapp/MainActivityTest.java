package com.example.udacitybakingapp;


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
public class DetailActivityTest {

    @Rule
    public IntentsTestRule<DetailActivity> activityTestRule = new IntentsTestRule<>(DetailActivity.class);

    @Test
    public void whenRecyclerViewItemClickedThenOpenStepActivity() throws InterruptedException {
        Thread.sleep(SECONDS.toMillis(2));
        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(VideoActivity.class.getName()));
    }
}
