package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.ViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspDrawer extends EspView {

    protected final int drawerLayout;

    public static EspDrawer byId(int drawerLayout, int drawerContent) {
        return new EspDrawer(drawerLayout, drawerContent);
    }

    @Deprecated // mark parent static method as not usable for this class
    public static EspView byId(int resourceId) {
        throw new UnsupportedOperationException();
    }

    public EspDrawer(int drawerLayout, int drawerContent) {
        super(drawerContent);
        this.drawerLayout = drawerLayout;
    }

    public EspDrawer(EspDrawer template) {
        super(template);
        this.drawerLayout = template.drawerLayout;
    }

    public void open() {
        findDrawerLayout().perform(ViewActions.actionWithAssertions(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER_RIGHT, Press.FINGER)));
    }

    public void close() {
        findDrawerLayout().perform(ViewActions.actionWithAssertions(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_RIGHT, GeneralLocation.CENTER_LEFT, Press.FINGER)));
    }

    protected ViewInteraction findDrawerLayout() {
        return onView(withId(drawerLayout));
    }
}
