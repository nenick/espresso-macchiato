package de.nenick.espressomacchiato.tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.collect.Lists;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

class EspScreenshotToolPreJellyBeanMr2 {

    public void takeScreenShot(File screenshot) throws Exception {
        long start = System.currentTimeMillis();
        View recentDecorView;
        while ((recentDecorView = getRecentDecorView(getWindowDecorViews())) == null) {
            if (System.currentTimeMillis() - start < 30000) break;
            Thread.sleep(100);
        }

        takeScreenShot(getCurrentActivity(), recentDecorView, screenshot);
    }

    public static ArrayList<View> getWindowDecorViews() {

        Field viewsField;
        Field instanceField;
        try {
            viewsField = getWindowManager().getDeclaredField("mViews");
            instanceField = getWindowManager().getDeclaredField(getWindowManagerString());
            viewsField.setAccessible(true);
            instanceField.setAccessible(true);
            Object instance = instanceField.get(null);
            ArrayList<View> viewArrayList;
            try {
                //noinspection unchecked
                viewArrayList = (ArrayList<View>) viewsField.get(instance);
            } catch (ClassCastException e) {
                // strange on local pc viewsField.get returns an array list
                // on build agent it return just an array
                viewArrayList = Lists.newArrayList((View[]) viewsField.get(instance));
            }
            return viewArrayList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getWindowManager() {
        try {
            String windowManagerClassName;
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                windowManagerClassName = "android.view.WindowManagerGlobal";
            } else {
                windowManagerClassName = "android.view.WindowManagerImpl";
            }
            return Class.forName(windowManagerClassName);

        } catch (ClassNotFoundException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getWindowManagerString() {

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            return "sDefaultWindowManager";

        } else if (android.os.Build.VERSION.SDK_INT >= 13) {
            return "sWindowManager";

        } else {
            return "mWindowManager";
        }
    }

    public static View getRecentDecorView(ArrayList<View> views) {
        if (views == null || views.isEmpty()) {
            throw new RuntimeException("Error in getRecentDecorView: 0 views passed in.");
            //LOG.error("Error in getRecentDecorView: 0 views passed in.");
            //return null;
        }

        final View[] decorViews = new View[views.size()];
        int i = 0;
        View view;

        for (int j = 0; j < views.size(); j++) {
            view = views.get(j);
            if (view != null && view.getClass().getName()
                    //.equals("com.android.internal.policy.PhoneWindow$DecorView")) {
                    .equals("com.android.internal.policy.impl.PhoneWindow$DecorView")) {
                decorViews[i] = view;
                i++;
            }
        }
        return getRecentContainer(decorViews);
    }

    private static View getRecentContainer(View[] views) {
        View container = null;
        long drawingTime = 0;

        for (View view : views) {
            if (view != null && view.isShown() && view.hasWindowFocus() && view.getDrawingTime() > drawingTime) {
                container = view;
                drawingTime = view.getDrawingTime();
            }
        }
        return container;
    }

    private static void takeScreenShot(Activity activity, final View view, File screenshot) throws Exception {

        if (view == null) {
            throw new IllegalStateException("takeScreenShot: view from getWindowDecorViews() is null.");
        }

        final BitmapResult screen = new BitmapResult();

        if (Looper.myLooper() == Looper.getMainLooper()) {
            // On main thread already, Just Do It™.
            screen.bitmap = takeScreenShot(view);
        } else {
            // On a background thread, post to main.
            final CountDownLatch latch = new CountDownLatch(1);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        screen.bitmap = takeScreenShot(view);
                    } finally {
                        latch.countDown();
                    }
                }
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException("Unable to get screenshot", e);
            }
        }

        FileOutputStream fos = new FileOutputStream(screenshot);
        screen.bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        //screenShotFile.setReadable(true, false);

        fos.flush();
        fos.close();
    }

    private static Bitmap takeScreenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    static class BitmapResult {
        Bitmap bitmap;
    }

    private static Activity currentActivity = null;

    public static Activity getCurrentActivity() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }
}
