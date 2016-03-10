package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

public class EspDrawer {

    protected final int drawerLayout;
    protected final int drawerContent;

    public EspDrawer(int drawerLayout, int drawerContent) {
        this.drawerLayout = drawerLayout;
        this.drawerContent = drawerContent;
    }

    public void checkIsVisible() {
        findDrawerContent().check(matches(isDisplayed()));
    }

    public void checkIsHidden() {
        findDrawerContent().check(matches(not(isDisplayed())));
    }

    public void open() {
        findDrawerLayout().perform(DrawerActions.open());
    }

    public void close() {
        findDrawerLayout().perform(DrawerActions.close());
    }

    protected ViewInteraction findDrawerContent() {
        return onView(withId(drawerContent));
    }

    protected ViewInteraction findDrawerLayout() {
        return onView(withId(drawerLayout));
    }
}
