package de.nenick.espressomacchiato.elements;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.NavigationDrawerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class EspDrawerTest extends EspressoTestCase<NavigationDrawerActivity> {

    private static final int GROUP_ID_STANDARD = 10;
    private static final String navigationItemTitleStandard = "standard navigation item";
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
    public void testNavigationMenuItemByText() {
        addNavigationMenuItem();
        espDrawer.open();
        espDrawer.navigationMenuItem(navigationItemTitleStandard).assertIsDisplayedOnScreen();
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

    private void addNavigationMenuItem() {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                final NavigationView navigationView = (NavigationView) activityTestRule.getActivity().findViewById(NavigationDrawerActivity.drawerNavigationView);

                Menu menu = navigationView.getMenu();
                final MenuItem menuItemStandard = menu.add(GROUP_ID_STANDARD, 1, Menu.NONE, null);
                menuItemStandard.setTitle(navigationItemTitleStandard);
            }
        });
    }
}