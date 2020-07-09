package de.nenick.espressomacchiato.assertions.support;

import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import de.nenick.espressomacchiato.tools.EspResourceTool;

import static org.hamcrest.Matchers.is;

public class AppBarLayoutAssertion {

    public static ViewAssertion assertIsExpanded() {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                boolean isFullExpanded = appBarLayout.getHeight() - appBarLayout.getBottom() == 0;
                ViewMatchers.assertThat("is full expanded", isFullExpanded, is(true));
            }
        };
    }


    public static ViewAssertion assertIsCollapsed() {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                CollapsingToolbarLayout collapsingToolbarLayout = null;
                Toolbar toolbar = null;
                AppBarLayout appBarLayout = (AppBarLayout) view;

                for (int i = 0; i < appBarLayout.getChildCount(); i++) {
                    if(appBarLayout.getChildAt(i) instanceof CollapsingToolbarLayout) {
                        collapsingToolbarLayout = (CollapsingToolbarLayout) appBarLayout.getChildAt(i);
                    }
                }

                if(collapsingToolbarLayout == null) {
                    throw new IllegalStateException("Current only AppBarLayouts with CollapsingToolbarLayout are supported.");
                }

                for (int i = 0; i < collapsingToolbarLayout.getChildCount(); i++) {
                    if(collapsingToolbarLayout.getChildAt(i) instanceof Toolbar) {
                        toolbar = (Toolbar) collapsingToolbarLayout.getChildAt(i);
                    }
                }

                if(toolbar == null) {
                    throw new IllegalStateException("Current only CollapsingToolbarLayout with support Toolbar are supported.");
                }

                boolean isFullCollapsed = appBarLayout.getBottom() - toolbar.getHeight() == 0;
                isFullCollapsed |= appBarLayout.getBottom() - toolbar.getHeight() - EspResourceTool.getStatusBarHeight(view.getContext()) == 0;
                ViewMatchers.assertThat("is full collapsed", isFullCollapsed, is(true));
            }
        };
    }
}
