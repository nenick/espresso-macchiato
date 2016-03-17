package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspDrawer extends EspView {

    protected final int drawerLayout;

    public static EspDrawer byId(int drawerLayout, int drawerContent) {
        return new EspDrawer(drawerLayout, drawerContent);
    }

    /** hide {@link EspView#byId(int)} */
    @Deprecated
    public static EspView byId(int resourceId) {
        throw new UnsupportedOperationException();
    }

    public EspDrawer(int drawerLayout, int drawerContent) {
        super(drawerContent);
        this.drawerLayout = drawerLayout;
    }

    public void open() {
        findDrawerLayout().perform(DrawerActions.open());
    }

    public void close() {
        findDrawerLayout().perform(DrawerActions.close());
    }

    protected ViewInteraction findDrawerLayout() {
        return onView(withId(drawerLayout));
    }
}
