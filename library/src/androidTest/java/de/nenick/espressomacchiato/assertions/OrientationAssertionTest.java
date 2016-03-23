package de.nenick.espressomacchiato.assertions;

import android.content.res.Configuration;
import android.support.test.espresso.NoMatchingViewException;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

public class OrientationAssertionTest extends EspressoTestCase<BaseActivity> {

    @Test
    public void testCheck() throws Exception {
        exception.expect(NoMatchingViewException.class);
        onView(withId(android.R.id.text1)).check(OrientationAssertion.isOrientationPotrait());
    }

    @Test
    public void testOrientationAsString() {
        assertEquals(OrientationAssertion.orientationAsString(0), "UNDEFINED");
        assertEquals(OrientationAssertion.orientationAsString(Configuration.ORIENTATION_PORTRAIT), "PORTRAIT");
        assertEquals(OrientationAssertion.orientationAsString(Configuration.ORIENTATION_LANDSCAPE), "LANDSCAPE");
    }
}