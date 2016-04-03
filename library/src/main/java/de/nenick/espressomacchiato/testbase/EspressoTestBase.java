package de.nenick.espressomacchiato.testbase;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.base.DefaultFailureHandler;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@RunWith(AndroidJUnit4.class)
abstract class EspressoTestBase<A extends Activity> {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public abstract A getActivity();

    @Before
    public void setupEspresso() {
        Espresso.setFailureHandler(new CustomFailureHandler(getActivity()));
        avoidLockScreen();

        // "java.lang.RuntimeException: Waited for the root of the view hierarchy to have window focus and not be requesting layout for over 10 seconds. If you specified a non default root matcher, it may be picking a root that never takes focus. Otherwise, something is seriously wrong. Selected Root:
        // Root{application-window-token=android.view.ViewRootImpl$W@b2047918, window-token=android.view.ViewRootImpl$W@b2047918, has-window-focus=false, layout-params-type=1, layout-params-string=WM.LayoutParams{(0,0)(fillxfill) sim=#100 ty=1 fl=#1810100 pfl=0x8 wanim=0x10302a1}, decor-view-string=DecorView{id=-1, visibility=VISIBLE, width=480, height=800, has-focus=true, has-focusable=true, has-window-focus=false, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=1}}"
        // https://groups.google.com/forum/#!msg/android-test-kit-discuss/yIEwus_hjeY/xI1qpehDa7MJ -> looks like there's a dialog that says "Launcher isn't responding. Do you want to close it?" with options Wait and OK
    }

    @After
    public void cleanupEspresso() throws Exception {
        CloseAllActivitiesFunction.apply(InstrumentationRegistry.getInstrumentation());
    }

    protected void addViewToActivity(final View view, @IdRes final int targetLayoutId) {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup layout = (ViewGroup) getActivity().findViewById(targetLayoutId);
                layout.addView(view);
            }
        });
    }

    protected void performOnUiThread(Runnable runnable) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(runnable);
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
                Activity activity = getActivity();
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

    private class CustomFailureHandler implements FailureHandler {
        private final FailureHandler delegate;

        public CustomFailureHandler(Context targetContext) {
            delegate = new DefaultFailureHandler(targetContext);
        }

        @Override
        public void handle(Throwable error, Matcher<View> viewMatcher) {
            //ScreenShot.take();
            delegate.handle(error, viewMatcher);
        }
    }

    public void skipTestIfBelowAndroidMarshmallow() {
        Assume.assumeThat(Build.VERSION.SDK_INT,Matchers.greaterThanOrEqualTo(Build.VERSION_CODES.M));
    }
}
