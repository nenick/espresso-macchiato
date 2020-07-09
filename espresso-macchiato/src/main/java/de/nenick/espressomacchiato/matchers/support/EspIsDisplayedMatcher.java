package de.nenick.espressomacchiato.matchers.support;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import androidx.test.espresso.core.deps.guava.base.Preconditions;
import androidx.test.espresso.matcher.ViewMatchers;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import de.nenick.espressomacchiato.tools.EspResourceTool;

import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;


public class EspIsDisplayedMatcher {


    /**
     * Assume visible area percentage.
     *
     * There is an bug on pre Android v11 versions where actionbar height can't be resolved when
     * using appcompat actionbar. This method use the appcompat actionbar height attribute when
     * on pre v11 versions.
     *
     * @see ViewMatchers#isDisplayingAtLeast(int)
     */
    public static Matcher<View> isDisplayingAtLeast(final int areaPercentage) {
        Preconditions.checkState(areaPercentage <= 100, "Cannot have over 100 percent: %s", areaPercentage);
        Preconditions.checkState(areaPercentage > 0, "Must have a positive, non-zero value: %s", areaPercentage);
        return new IsDisplayingAtLeastMatcher(areaPercentage);
    }

    private static class IsDisplayingAtLeastMatcher extends TypeSafeMatcher<View> {
        private final int areaPercentage;

        public IsDisplayingAtLeastMatcher(int areaPercentage) {
            this.areaPercentage = areaPercentage;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(String.format("at least %s percent of the view's area is displayed to the user.", areaPercentage));
        }

        @Override
        public boolean matchesSafely(View view) {
            // get visible area for selected view
            // it also tell us if at least a small part visible, when not we can abort here
            Rect visibleParts = new Rect();
            boolean isAtLeastSomethingVisible = view.getGlobalVisibleRect(visibleParts);
            if (!isAtLeastSomethingVisible) {
                return false;
            }

            Rect screen = getScreenWithoutStatusBarActionBar(view);
            int viewHeight = (view.getHeight() > screen.height()) ? screen.height() : view.getHeight();
            int viewWidth = (view.getWidth() > screen.width()) ? screen.width() : view.getWidth();

            double maxArea = viewHeight * viewWidth;
            double visibleArea = visibleParts.height() * visibleParts.width();
            int displayedPercentage = (int) ((visibleArea / maxArea) * 100);
            boolean isViewFullVisible = displayedPercentage >= areaPercentage;
            return isViewFullVisible && withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE).matches(view);
        }

        private Rect getScreenWithoutStatusBarActionBar(View view) {
            DisplayMetrics m = new DisplayMetrics();
            ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(m);

            int statusBarHeight = EspResourceTool.getStatusBarHeight(view.getContext());
            int actionBarHeight = getActionBarHeight(view);

            return new Rect(0, 0, m.widthPixels, m.heightPixels - (statusBarHeight + actionBarHeight));
        }

        @TargetApi(11)
        private int getActionBarHeight(View view) {
            TypedValue tv = new TypedValue();
            boolean resolveAttribute;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                resolveAttribute = view.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
            } else {
                resolveAttribute = view.getContext().getTheme().resolveAttribute(androidx.appcompat.appcompat.R.attr.actionBarSize, tv, true);
            }

            int actionBarHeight = 0;
            if(resolveAttribute) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, view.getContext().getResources().getDisplayMetrics());
            }
            return actionBarHeight;
        }
    }
}
