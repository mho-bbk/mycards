package com.example.mycards;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * First instrumental test
 *
 * @see <a href="https://developer.android.com/training/testing/espresso/setup#java">Espresso Setup Instructions</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FlashcardDisplayInstrumentalTest {

    //Do I need this?
//    @Rule
//    public ActivityScenarioRule<CardDisplayFragment> activityRule =
//            new ActivityScenarioRule<>(CardDisplayFragment.class);

    //Passes if text is displayed, but does not assess whether text is displayed in particular Fragment
    @Test
    public void testSideADisplay() {
        onView(withText("Side A")).check(matches(isDisplayed()));
    }
}