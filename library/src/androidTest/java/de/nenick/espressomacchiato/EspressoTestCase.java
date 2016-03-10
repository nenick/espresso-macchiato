package de.nenick.espressomacchiato;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import de.nenick.espressomacchiato.test.views.BaseActivity;

@RunWith(AndroidJUnit4.class)
public abstract class EspressoTestCase {

    @Rule
    public ActivityTestRule<BaseActivity> activityTestRule = new ActivityTestRule<>(BaseActivity.class);

}
