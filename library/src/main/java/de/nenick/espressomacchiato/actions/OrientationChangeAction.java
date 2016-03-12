package de.nenick.espressomacchiato.actions;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
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
 * An Espresso ViewAction that changes the orientation of the screen
 * <p/>
 * See original source at https://gist.github.com/nbarraille/03e8910dc1d415ed9740
 */
public class OrientationChangeAction implements ViewAction {

    private final int orientation;

    private OrientationChangeAction(int orientation) {
        this.orientation = orientation;
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
        return "change orientation to " + orientation;
    }

    @Override
    public void perform(UiController uiController, View view) {
        final Activity activity = (Activity) view.getContext();
        if (hasActivityFixedOrientation(activity)) {
            return;
        }
        uiController.loopMainThreadUntilIdle();

        activity.setRequestedOrientation(orientation);
        Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
        if (resumedActivities.isEmpty()) {
            throw new IllegalStateException("No activities in state resumed. That could mean orientation change failed.");
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

            // report if the activity orientation is fixed
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
