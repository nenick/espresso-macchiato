package de.nenick.espressomacchiato.testbase;

import android.app.Activity;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.lifecycle.Stage;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import de.nenick.espressomacchiato.elements.EspSystemAerrDialog;
import de.nenick.espressomacchiato.elements.EspSystemAnrDialog;

@RunWith(AndroidJUnit4.class)
public abstract class EspressoTestBase<A extends Activity> {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public abstract A getActivity();

    public static Activity currentActivity() {
        class Holder {
            private Activity activity;
        }
        final Holder holder = new Holder();
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            Set<Activity> activitiesInStages = EspCloseAllActivitiesFunction.getActivitiesInStages(Stage.RESUMED, Stage.PAUSED);
            if (activitiesInStages.iterator().hasNext()) {
                holder.activity = activitiesInStages.iterator().next();
            }
        } else {
            InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
                public void run() {
                    Set<Activity> activitiesInStages = EspCloseAllActivitiesFunction.getActivitiesInStages(Stage.RESUMED, Stage.PAUSED);
                    if (activitiesInStages.iterator().hasNext()) {
                        holder.activity = activitiesInStages.iterator().next();
                    }
                }
            });
        }
        return holder.activity;
    }

    @Before
    public void setupEspresso() {
        //Espresso.setFailureHandler(new EspScreenshotFailureHandler(InstrumentationRegistry.getTargetContext()));
        //keepScreenUsable();
        //EspSystemAnrDialog.build().dismissIfShown();
        //EspSystemAerrDialog.build().dismissIfShown();
    }

    @After
    public void cleanupEspresso() throws Exception {
        EspCloseAllActivitiesFunction.apply(InstrumentationRegistry.getInstrumentation());
    }

    protected void addViewToLayoutReuseUiThread(final View view, @IdRes final int targetLayoutId) {
        ViewGroup layout = (ViewGroup) getActivity().findViewById(targetLayoutId);
        layout.addView(view);
    }

    protected void addViewToLayout(final View view, @IdRes final int targetLayoutId) {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup layout = (ViewGroup) getActivity().findViewById(targetLayoutId);
                layout.addView(view);
            }
        });
    }

    protected void addDialog(final AlertDialog.Builder dialog) {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    protected void addDialog(final android.app.AlertDialog.Builder dialog) {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    protected void performOnUiThread(Runnable runnable) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(runnable);
    }

    private void keepScreenUsable() {
        // sometimes tests failed on emulator because lock screen is shown / screen turns of
        // java.lang.RuntimeException: Waited for the root of the view hierarchy to have window focus and not be requesting layout for over 10 seconds. If you specified a non default root matcher, it may be picking a root that never takes focus. Otherwise, something is seriously wrong"
        // http://stackoverflow.com/questions/26139070/testui-jenkins-using-espresso
        // http://stackoverflow.com/questions/22737476/false-positives-junit-framework-assertionfailederror-edittext-is-not-found
        // http://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#FLAG_SHOW_WHEN_LOCKED
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Activity activity = getActivity();
                if (activity == null) {
                    // happens if activity is not started @Before, rather somewhere inside test execution
                    return;
                }
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }

    protected Class<A> getGenericActivityClass() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            //noinspection unchecked
            return ((Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        } else {
            throw new IllegalArgumentException("Please provide generic start activity for " + getClass().getSimpleName() + " (e.g. extends EspressoTestCase<MyStartActivity>)");
        }
    }

    public void skipTestIfBelowAndroidMarshmallow() {
        Assume.assumeThat(Build.VERSION.SDK_INT, Matchers.greaterThanOrEqualTo(Build.VERSION_CODES.M));
    }

    /*public <T> void assertThat(final T actual, final Matcher<? super T> expected) {
        onView(isRoot()).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                Assert.assertThat(actual, expected);
            }
        });
    }*/
}
