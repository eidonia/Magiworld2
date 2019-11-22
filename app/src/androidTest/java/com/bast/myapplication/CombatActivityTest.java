package com.bast.myapplication;


import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)

public class CombatActivityTest {
    @Rule
    public ActivityTestRule<CombatActivity> activityActivityTestRule = new ActivityTestRule<>(CombatActivity.class, true, true);
    Mage joueur1 = new Mage("mage", "bloup", 25, 100, 0, 0, 25, "Boule de Feu", "Soin");
    Mage joueur2 = new Mage("mage", "bloup", 25, 100, 0, 0, 25, "Boule de Feu", "Soin");

    @Test
    public void onClick(){
        onView(withId(R.id.textPres)).check(matches(not(withText(""))));

        onView(withId(R.id.buttonAttBase)).perform(click());
        onView(withId(R.id.textInfoJ1)).check(matches(not(withText(""))));
        onView(withId(R.id.buttonAttSpe)).check((ViewAssertion) not(isClickable()));
        onView(withId(R.id.buttonAttBase)).check((ViewAssertion) not(isClickable()));

        onView(withId(R.id.buttonAttSpe)).perform(click());
        onView(withId(R.id.textInfoJ1)).check(matches(not(withText(""))));
        onView(withId(R.id.buttonAttSpe)).check((ViewAssertion) not(isClickable()));
        onView(withId(R.id.buttonAttBase)).check((ViewAssertion) not(isClickable()));

        onView(withId(R.id.buttonAttBaseJ2)).perform(click());
        onView(withId(R.id.textInfoJ2)).check(matches(not(withText(""))));
        onView(withId(R.id.buttonAttSpeJ2)).check((ViewAssertion) not(isClickable()));
        onView(withId(R.id.buttonAttBaseJ2)).check((ViewAssertion) not(isClickable()));

        onView(withId(R.id.buttonAttSpeJ2)).perform(click());
        onView(withId(R.id.textInfoJ2)).check(matches(not(withText(""))));
        onView(withId(R.id.buttonAttSpeJ2)).check((ViewAssertion) not(isClickable()));
        onView(withId(R.id.buttonAttBaseJ2)).check((ViewAssertion) not(isClickable()));

        onView(withId(R.id.buttonFinRound)).perform(click());
        onView(withId(R.id.textInfoJ1)).check(matches(withText("")));
        onView(withId(R.id.buttonAttSpe)).check((ViewAssertion) isClickable());
        onView(withId(R.id.buttonAttBase)).check((ViewAssertion) isClickable());
        onView(withId(R.id.textInfoJ2)).check(matches(withText("")));
        onView(withId(R.id.buttonAttSpeJ2)).check((ViewAssertion) isClickable());
        onView(withId(R.id.buttonAttBaseJ2)).check((ViewAssertion) isClickable());

    }

}
