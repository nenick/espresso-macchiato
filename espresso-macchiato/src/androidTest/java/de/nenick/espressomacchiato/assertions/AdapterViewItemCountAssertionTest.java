package de.nenick.espressomacchiato.assertions;

import android.support.test.espresso.NoMatchingViewException;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/** Basic test */
public class AdapterViewItemCountAssertionTest extends EspressoTestCase<BaseActivity> {

    @Test
    public void testCheckFailure() throws Exception {
        exception.expect(NoMatchingViewException.class);
        onView(withId(android.R.id.text1)).check(new AdapterViewItemCountAssertion(13));
    }
}