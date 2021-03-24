package com.example.esp_androidx_drawer;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;

import de.nenick.espressomacchiato.elements.support.EspNavigationMenuItem;
import de.nenick.espressomacchiato.tools.EspWait;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Actions and assertions for a Drawer.
 *
 * @since Espresso Macchiato 0.1
 */
public class EspDrawer {

    protected final int drawerLayout;

    /**
     * Create new instance matching an element with given resources.
     *
     * @param drawerLayout  Identifier for base drawer layout.
     * @param drawerContent Identifier for base layout with drawer content.
     *
     * @return New instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.1
     */
    public static EspDrawer byId(int drawerLayout, int drawerContent) {
        return new EspDrawer(drawerLayout, drawerContent);
    }

    /**
     * Create new instance matching an element with given resources.
     *
     * @param drawerLayout  Identifier for base drawer layout.
     * @param drawerContent Identifier for base layout with drawer content.
     *
     * @since Espresso Macchiato 0.1
     */
    public EspDrawer(int drawerLayout, int drawerContent) {
        super(drawerContent);
        this.drawerLayout = drawerLayout;
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspDrawer(EspDrawer template) {
        super(template);
        this.drawerLayout = template.drawerLayout;
    }

    /**
     * Open the drawer.
     *
     * @since Espresso Macchiato 0.1
     */
    public void open() {
        findDrawerLayout().perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER_RIGHT, Press.FINGER));
        // Espresso does not wait until open animation ended. Try to stabilize it.
        EspWait.forIdle();
    }

    /**
     * Close the drawer.
     *
     * @since Espresso Macchiato 0.1
     */
    public void close() {
        findDrawerLayout().perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_RIGHT, GeneralLocation.CENTER_LEFT, Press.FINGER));
    }

    /**
     * Access base drawer layout.
     *
     * @since Espresso Macchiato 0.1
     */
    protected ViewInteraction findDrawerLayout() {
        return Espresso.onView(ViewMatchers.withId(drawerLayout));
    }

    /**
     * Access navigation menu item by his text.
     *
     * @param text Navigation menu item text.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.6
     */
    public EspNavigationMenuItem navigationMenuItem(String text) {
        return EspNavigationMenuItem.byText(text);
    }

    /**
     * @since Espresso Macchiato 0.1
     * @deprecated Currently not supported please use a different element creator.
     */
    @Deprecated // mark parent static method as not usable for this class
    public static EspView byId(int resourceId) {
        throw new UnsupportedOperationException();
    }
}
