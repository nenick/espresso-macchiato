package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.NavigationDrawerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class EspDrawerTest extends EspressoTestCase<NavigationDrawerActivity> {

    private EspDrawer espDrawer = EspDrawer.byId(NavigationDrawerActivity.drawerLayout, NavigationDrawerActivity.drawerNavigationView);

    @Test
    public void testDrawer() {
        espDrawer.assertIsHidden();

        espDrawer.open();
        espDrawer.assertIsDisplayedOnScreen();

        espDrawer.close();
        espDrawer.assertIsHidden();
    }

    @Test
    public void testTemplateConstructor() {
        espDrawer = new EspDrawer(espDrawer);
        espDrawer.open();
        espDrawer.assertIsDisplayedOnScreen();
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testByIdObsolete() {
        exception.expect(UnsupportedOperationException.class);
        EspDrawer.byId(0);
    }
}