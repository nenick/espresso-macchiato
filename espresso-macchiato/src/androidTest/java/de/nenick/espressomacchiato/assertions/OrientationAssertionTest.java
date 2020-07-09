package de.nenick.espressomacchiato.assertions;

import android.content.res.Configuration;
import androidx.test.espresso.NoMatchingViewException;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/** Basic test */
public class OrientationAssertionTest extends EspressoTestCase<BaseActivity> {

    @Test
    public void testCheckFailure() throws Exception {
        exception.expect(NoMatchingViewException.class);
        onView(withId(android.R.id.text1)).check(OrientationAssertion.isOrientationPortrait());
    }

    @Test
    public void testOrientationAsString() {
        assertEquals(OrientationAssertion.orientationAsString(0), "UNDEFINED");
        assertEquals(OrientationAssertion.orientationAsString(Configuration.ORIENTATION_PORTRAIT), "PORTRAIT");
        assertEquals(OrientationAssertion.orientationAsString(Configuration.ORIENTATION_LANDSCAPE), "LANDSCAPE");
    }
}