package de.nenick.espressomacchiato.actions.support;

import android.support.design.widget.AppBarLayout;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;

public class AppBarLayoutAction {

    public static ViewAction collapse() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(AppBarLayout.class);
            }

            @Override
            public String getDescription() {
                return "Collapse AppBarLayout inside of a CollapsingToolbarLayout.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                appBarLayout.setExpanded(false, false);
            }
        };
    }

    public static ViewAction expand() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(AppBarLayout.class);
            }

            @Override
            public String getDescription() {
                return "Expand AppBarLayout inside of a CollapsingToolbarLayout.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                appBarLayout.setExpanded(true, false);
            }
        };
    }
}
