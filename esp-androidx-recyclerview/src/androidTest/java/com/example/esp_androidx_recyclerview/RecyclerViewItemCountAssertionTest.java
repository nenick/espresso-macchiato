package com.example.esp_androidx_recyclerview;

import androidx.test.espresso.NoMatchingViewException;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/** Basic test */
public class RecyclerViewItemCountAssertionTest extends EspressoTestCase<BaseActivity> {

    @Test
    public void testCheckFailure() throws Exception {
        exception.expect(NoMatchingViewException.class);
        Espresso.onView(ViewMatchers.withId(android.R.id.text1)).check(new RecyclerViewItemCountAssertion(13));
    }
}