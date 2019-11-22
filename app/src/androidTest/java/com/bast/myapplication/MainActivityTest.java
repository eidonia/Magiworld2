package com.bast.myapplication;

import android.content.Context;
import android.widget.RadioButton;

import androidx.test.espresso.action.ScrollToAction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)


public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, true);



    @Test
    public void onClick(){
        onView(withId(R.id.editNomPerso)).perform(replaceText("Koin"));
        onView(withId(R.id.editNivJ1)).perform(replaceText("25"));
        onView(withId(R.id.editForceJ1)).perform(replaceText("25"));
        onView(withId(R.id.editAgiJ1)).perform(replaceText("0"));
        onView(withId(R.id.editIntJ1)).perform(replaceText("0"));
        onView(withId(R.id.buttonCreateJ1)).perform(click());
        onView(withId(R.id.buttonRAZ)).perform(scrollTo()).perform(click());
        onView(withId(R.id.editNomPerso)).check(matches(withText("")));
        onView(withId(R.id.editNivJ1)).check(matches(withText("")));
        onView(withId(R.id.editForceJ1)).check(matches(withText("")));
        onView(withId(R.id.editAgiJ1)).check(matches(withText("")));
        onView(withId(R.id.editIntJ1)).check(matches(withText("")));
    }

}
