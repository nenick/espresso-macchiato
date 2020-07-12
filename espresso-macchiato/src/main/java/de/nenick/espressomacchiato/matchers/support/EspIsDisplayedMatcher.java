package de.nenick.espressomacchiato.matchers.support;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;
import androidx.test.espresso.remote.annotation.RemoteMsgField;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Locale;

import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

/** Copies the original isDisplayingAtLeast behavior and adjust it for appcompat usage. */
// perhaps related to https://issuetracker.google.com/issues/37063391
public class EspIsDisplayedMatcher {


    /**
     * Assume visible area percentage.
     *
     * There is an bug on pre Android v21 versions where wrong actionbar height is resolved when
     * using appcompat. This method use the appcompat actionbar height when AppCompatActivity is used.
     *
     * @see ViewMatchers#isDisplayingAtLeast(int)
     */
    public static Matcher<View> isDisplayingAtLeast(final int areaPercentage) {
        Preconditions.checkState(areaPercentage <= 100, "Cannot have over 100 percent: %s", areaPercentage);
        Preconditions.checkState(areaPercentage > 0, "Must have a positive, non-zero value: %s", areaPercentage);
        return new IsDisplayingAtLeastMatcher(areaPercentage);
    }

    static final class IsDisplayingAtLeastMatcher extends TypeSafeMatcher<View> {

        @RemoteMsgField(order = 0)
        final int areaPercentage;

        @RemoteMsgConstructor
        private IsDisplayingAtLeastMatcher(int areaPercentage) {
            this.areaPercentage = areaPercentage;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(
                    String.format(
                            Locale.ROOT,
                            "at least %s percent of the view's area is displayed to the user.",
                            areaPercentage));
        }

        @Override
        public boolean matchesSafely(View view) {
            Rect visibleParts = new Rect();
            boolean visibleAtAll = view.getGlobalVisibleRect(visibleParts);
            if (!visibleAtAll) {
                return false;
            }

            Rect screen = getScreenWithoutStatusBarActionBar(view);

            float viewHeight = (view.getHeight() > screen.height()) ? screen.height() : view.getHeight();
            float viewWidth = (view.getWidth() > screen.width()) ? screen.width() : view.getWidth();

            if (Build.VERSION.SDK_INT >= 11) {
                // For API level 11 and above, factor in the View's scaleX and scaleY properties.
                viewHeight = Math.min(view.getHeight() * view.getScaleY(), screen.height());
                viewWidth = Math.min(view.getWidth() * view.getScaleX(), screen.width());
            }

            double maxArea = viewHeight * viewWidth;
            double visibleArea = visibleParts.height() * visibleParts.width();
            int displayedPercentage = (int) ((visibleArea / maxArea) * 100);

            return displayedPercentage >= areaPercentage
                    && withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE).matches(view);
        }

        private Rect getScreenWithoutStatusBarActionBar(View view) {
            DisplayMetrics m = new DisplayMetrics();
            ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay()
                    .getMetrics(m);

            // Get status bar height
            int resourceId =
                    view.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight =
                    (resourceId > 0) ? view.getContext().getResources().getDimensionPixelSize(resourceId) : 0;

            // Get action bar height
            TypedValue tv = new TypedValue();
            int actionBarSize = android.R.attr.actionBarSize;
            if(view.getContext() instanceof AppCompatActivity) {
                actionBarSize = androidx.appcompat.R.attr.actionBarSize;
            }
            int actionBarHeight =
                    view.getContext().getTheme().resolveAttribute(actionBarSize, tv, true)
                            ? TypedValue.complexToDimensionPixelSize(
                            tv.data, view.getContext().getResources().getDisplayMetrics())
                            : 0;

            return new Rect(0, 0, m.widthPixels, m.heightPixels - (statusBarHeight + actionBarHeight));
        }
    }
}
