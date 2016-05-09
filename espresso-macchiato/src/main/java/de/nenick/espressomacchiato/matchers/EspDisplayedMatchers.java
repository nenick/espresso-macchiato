package de.nenick.espressomacchiato.matchers;

import android.content.Context;
import android.graphics.Rect;
import android.support.test.espresso.matcher.ViewMatchers;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

public class EspDisplayedMatchers {

    /**
     * Returns a matcher which accepts a view which is not displayed.
     * That means the view would only be visible when you scroll to it.
     */
    public static Matcher<View> isNotDisplaying() {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("No part of the view's area is displayed to the user.");
            }

            @Override
            public boolean matchesSafely(View view) {
                Rect visibleParts = new Rect();
                boolean visibleAtAll = view.getGlobalVisibleRect(visibleParts);
                if (!visibleAtAll) {
                    return true;
                }

                Rect screen = getScreenWithoutStatusBarActionBar(view);
                int viewHeight = (view.getHeight() > screen.height()) ? screen.height() : view.getHeight();
                int viewWidth = (view.getWidth() > screen.width()) ? screen.width() : view.getWidth();

                double maxArea = viewHeight * viewWidth;
                double visibleArea = visibleParts.height() * visibleParts.width();
                int displayedPercentage = (int) ((visibleArea / maxArea) * 100);

                return displayedPercentage == 0
                        && withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE).matches(view);
            }

            private Rect getScreenWithoutStatusBarActionBar(View view) {
                DisplayMetrics m = new DisplayMetrics();
                ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE))
                        .getDefaultDisplay().getMetrics(m);

                // Get status bar height
                int resourceId = view.getContext().getResources()
                        .getIdentifier("status_bar_height", "dimen", "android");
                int statusBarHeight = (resourceId > 0) ? view.getContext().getResources()
                        .getDimensionPixelSize(resourceId) : 0;

                // Get action bar height
                TypedValue tv = new TypedValue();
                int actionBarHeight = (view.getContext().getTheme().resolveAttribute(
                        android.R.attr.actionBarSize, tv, true)) ? TypedValue.complexToDimensionPixelSize(
                        tv.data, view.getContext().getResources().getDisplayMetrics()) : 0;

                return new Rect(0, 0, m.widthPixels, m.heightPixels - (statusBarHeight + actionBarHeight));
            }
        };
    }
}
