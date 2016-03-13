package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.NavigationDrawerActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspDrawerTest extends EspressoTestCase<NavigationDrawerActivity> {

    private EspDrawer espDrawer = new EspDrawer(NavigationDrawerActivity.drawerLayout, NavigationDrawerActivity.drawerContentLayout);

    @Test
    public void testDrawer() {
        espDrawer.assertIsHidden();

        espDrawer.open();
        espDrawer.assertIsVisible();

        espDrawer.close();
        espDrawer.assertIsHidden();
    }
}