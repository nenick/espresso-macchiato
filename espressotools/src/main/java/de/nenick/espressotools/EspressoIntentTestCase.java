package de.nenick.espressotools;

import android.app.Activity;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;

public abstract class EspressoIntentTestCase<A extends Activity> extends EspressoTestBase<A> {

    @Rule
    public IntentsTestRule<A> activityTestRule = new IntentsTestRule<>(getGenericActivityClass());

    @Override
    public A getActivity() {
        return activityTestRule.getActivity();
    }
}
