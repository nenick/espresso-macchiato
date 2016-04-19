package de.nenick.espressomacchiato.testbase;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

public abstract class EspressoTestCase<A extends Activity> extends EspressoTestBase<A> {

    @Rule
    public ActivityTestRule<A> activityTestRule = new ActivityTestRule<>(getGenericActivityClass());

    @Override
    public A getActivity() {
        return activityTestRule.getActivity();
    }
}
