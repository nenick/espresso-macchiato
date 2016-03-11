package de.nenick.espressomacchiato;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import de.nenick.espressomacchiato.test.views.BaseActivity;

@RunWith(AndroidJUnit4.class)
public abstract class EspressoTestCase {

    @Rule
    public ActivityTestRule<BaseActivity> activityTestRule = new ActivityTestRule<>(BaseActivity.class);

    @Before
    public void setupEspresso() {
        avoidLockScreen();
    }

    @After
    public void cleanupEspresso() throws Exception {
        CloseAllActivitiesFunction.apply(InstrumentationRegistry.getInstrumentation());
    }

    private void avoidLockScreen() {
        // sometimes tests failed on emulator because lock screen is shown
        // java.lang.RuntimeException: Waited for the root of the view hierarchy to have window focus and not be requesting layout for over 10 seconds. If you specified a non default root matcher, it may be picking a root that never takes focus. Otherwise, something is seriously wrong"
        // http://stackoverflow.com/questions/26139070/testui-jenkins-using-espresso
        // http://stackoverflow.com/questions/22737476/false-positives-junit-framework-assertionfailederror-edittext-is-not-found
        // http://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#FLAG_SHOW_WHEN_LOCKED
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Activity activity = activityTestRule.getActivity();
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }
}
