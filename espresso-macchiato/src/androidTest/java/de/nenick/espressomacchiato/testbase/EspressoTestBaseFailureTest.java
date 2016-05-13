package de.nenick.espressomacchiato.testbase;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;

import static org.junit.Assert.assertNull;

public class EspressoTestBaseFailureTest extends EspressoTestBase<BaseActivity> {

    @Rule
    public ActivityTestRule<BaseActivity> activityTestRule = new ActivityTestRule<>(BaseActivity.class);

    @Override
    public BaseActivity getActivity() {
        return activityTestRule.getActivity();
    }

    @Test
    public void testCurrentActivity() {
        assertNull(currentActivity());
    }

    @Test
    public void testGetGenericActivityFailure() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Please provide generic start activity for " + getClass().getSimpleName() + " (e.g. extends EspressoTestCase<MyStartActivity>)");
        getGenericActivityClass();
    }
}