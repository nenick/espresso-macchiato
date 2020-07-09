package de.nenick.espressomacchiato.actions;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.testbase.EspressoTestBase;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * An Espresso ViewAction that changes the requestedOrientation of the screen
 * <p>
 * See original source at https://gist.github.com/nbarraille/03e8910dc1d415ed9740
 */
public class OrientationChangeAction implements ViewAction {

    private final int requestedOrientation;

    private OrientationChangeAction(int orientation) {
        this.requestedOrientation = orientation;
    }

    public static OrientationChangeAction orientationLandscape() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static OrientationChangeAction orientationPortrait() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public Matcher<View> getConstraints() {
        return isRoot();
    }

    @Override
    public String getDescription() {
        return "change orientation to " + requestedOrientation;
    }

    @Override
    public void perform(UiController uiController, View view) {
        final Activity activity = EspressoTestBase.currentActivity();


        // change rotation programmatically ignores android manifest configurations
        if (hasActivityFixedOrientation(activity)) {
            return;
        }

        activity.setRequestedOrientation(requestedOrientation);

        // wait until rotation is done, espresso checks and actions don't wait
        // sometimes activity is not rotated when next check or action is performed
        int requestedOrientation = this.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ? Configuration.ORIENTATION_LANDSCAPE : Configuration.ORIENTATION_PORTRAIT;
        while (InstrumentationRegistry.getContext().getResources().getConfiguration().orientation != requestedOrientation) {
            uiController.loopMainThreadForAtLeast(30);
        }
    }

    protected boolean hasActivityFixedOrientation(Activity currentActivity) {
        ActivityInfo[] activities;
        String packageName = currentActivity.getPackageName();

        // collect AndroidManifest.xml activities properties
        try {
            activities = InstrumentationRegistry.getTargetContext().getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities;
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }

        for (ActivityInfo activity : activities) {

            // skip if activity info is not for the current active activity
            if (!activity.name.equals(currentActivity.getClass().getName())) {
                continue;
            }

            // report if the activity requestedOrientation is fixed
            if (activity.screenOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
                Log.d(OrientationChangeAction.class.getSimpleName(), "Ignore orientation change because orientation for this activity is fixed in AndroidManifest.xml.");
                return true;
            }
        }

        return false;
    }
}
