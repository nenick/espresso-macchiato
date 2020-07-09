package de.nenick.espressomacchiato.testbase;

import android.app.Activity;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;

public abstract class EspressoIntentTestCase<A extends Activity> extends EspressoTestBase<A> {

    @Rule
    public IntentsTestRule<A> activityTestRule = new IntentsTestRule<>(getGenericActivityClass());

    @Override
    public A getActivity() {
        return activityTestRule.getActivity();
    }
}
