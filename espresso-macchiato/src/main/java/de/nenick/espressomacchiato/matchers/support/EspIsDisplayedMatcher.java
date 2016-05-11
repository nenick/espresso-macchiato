package de.nenick.espressomacchiato.matchers.support;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.test.espresso.core.deps.guava.base.Preconditions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static org.hamcrest.Matchers.is;

public class EspIsDisplayedMatcher {
    public static Matcher<View> isDisplayingAtLeast(final int areaPercentage) {
        Preconditions.checkState(areaPercentage <= 100, "Cannot have over 100 percent: %s", areaPercentage);
        Preconditions.checkState(areaPercentage > 0, "Must have a positive, non-zero value: %s", areaPercentage);
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("at least %s percent of the view's area is displayed to the user.", areaPercentage));
            }

            @Override
            public boolean matchesSafely(View view) {
                Rect visibleParts = new Rect();
                boolean visibleAtAll = view.getGlobalVisibleRect(visibleParts);
                if (!visibleAtAll) {
                    return false;
                }

                Rect screen = getScreenWithoutStatusBarActionBar(view);
                int viewHeight = (view.getHeight() > screen.height()) ? screen.height() : view.getHeight();
                int viewWidth = (view.getWidth() > screen.width()) ? screen.width() : view.getWidth();

                double maxArea = viewHeight * viewWidth;
                double visibleArea = visibleParts.height() * visibleParts.width();
                int displayedPercentage = (int) ((visibleArea / maxArea) * 100);

                return displayedPercentage >= areaPercentage && withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE).matches(view);
            }

            private Rect getScreenWithoutStatusBarActionBar(View view) {
                DisplayMetrics m = new DisplayMetrics();
                ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(m);

                // Get status bar height
                int resourceId = view.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
                int statusBarHeight = (resourceId > 0) ? view.getContext().getResources().getDimensionPixelSize(resourceId) : 0;

                // Get action bar height
                TypedValue tv = new TypedValue();


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    boolean resolveAttribute = view.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
                    assertThat(resolveAttribute, is(true));
                } else {
                    boolean resolveAttribute = view.getContext().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true);
                    assertThat(resolveAttribute, is(true));
                }

                int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, view.getContext().getResources().getDisplayMetrics());
                return new Rect(0, 0, m.widthPixels, m.heightPixels - (statusBarHeight + actionBarHeight));
            }
        };
    }
}
