package de.nenick.espressomacchiato.testbase;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;

import static org.junit.Assert.assertNotNull;

/** Basic test */
public class EspressoTestBaseFailureTest extends EspressoTestBase {

    @Rule
    public ActivityTestRule<BaseActivity> activityTestRule = new ActivityTestRule<>(BaseActivity.class);

    @Override
    public BaseActivity getActivity() {
        return activityTestRule.getActivity();
    }

    @Test
    public void testCurrentActivity() {
        assertNotNull(currentActivity());
    }

    @Test
    public void testGetGenericActivityFailure() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Please provide generic start activity for " + getClass().getSimpleName() + " (e.g. extends EspressoTestCase<MyStartActivity>)");
        getGenericActivityClass();
    }
}