package de.nenick.espressomacchiato.actions;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.Collection;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * An Espresso ViewAction that changes the requestedOrientation of the screen
 * <p/>
 * See original source at https://gist.github.com/nbarraille/03e8910dc1d415ed9740
 */
public class OrientationChangeAction implements ViewAction {

    private final int requestedOrientation;

    private OrientationChangeAction(int orientation) {
        this.requestedOrientation = orientation;
    }

    public static ViewAction orientationLandscape() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static ViewAction orientationPortrait() {
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
        final Activity activity = (Activity) view.getContext();

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

    private boolean hasActivityFixedOrientation(Activity currentActivity) {
        ActivityInfo[] activities;
        String packageName = currentActivity.getPackageName();

        // collect AndroidManifest.xml activities properties
        try {
            activities = currentActivity.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities;
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("No activities found for " + packageName + " in AndroidManifest.xml", e);
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
            } else {
                return false;
            }
        }

        throw new IllegalStateException("Activity configuration for " + currentActivity.getClass().getSimpleName() + " not found in AndroidManifest.xml");
    }
}
