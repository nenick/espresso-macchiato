package de.nenick.espressomacchiato.elements;

import android.support.design.widget.NavigationView;
import android.support.test.espresso.NoMatchingViewException;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.NavigationDrawerActivity;
import de.nenick.espressotools.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspNavigationMenuItemTest extends EspressoTestCase<NavigationDrawerActivity> {

    private static final String navigationItemTitleStandard = "standard navigation item";
    private static final String navigationItemTitleCustomActionView = "custom action view navigation item";
    private static final String initialClickFeedbackText = "default";
    private static final int GROUP_ID_STANDARD = 10;
    private static final int GROUP_ID_CUSTOM_ACTION_VIEW = 20;
    private static final int textViewClickFeedbackId = android.R.id.text2;

    private EspTextView espTextViewClickFeedback = EspTextView.byId(textViewClickFeedbackId);
    private EspDrawer espDrawer = EspDrawer.byId(NavigationDrawerActivity.drawerLayout, NavigationDrawerActivity.drawerNavigationView);
    private EspNavigationMenuItem espNavigationMenuItemStandard = EspNavigationMenuItem.byText(navigationItemTitleStandard);
    private EspNavigationMenuItem espNavigationMenuItemCustomActionView = EspNavigationMenuItem.byText(navigationItemTitleCustomActionView);

    @Before
    public void setup() {
        givenSomeNavigationItems();
    }

    @Test
    public void testClickStandard() {
        espTextViewClickFeedback.assertTextIs(initialClickFeedbackText);
        espDrawer.open();

        espNavigationMenuItemStandard.assertIsNotSelected();
        espNavigationMenuItemStandard.click();
        espNavigationMenuItemStandard.assertIsSelected();
        espTextViewClickFeedback.assertTextIs(navigationItemTitleStandard);
    }

    @Test
    public void testClickCustomActionView() {
        espTextViewClickFeedback.assertTextIs(initialClickFeedbackText);
        espDrawer.open();

        espNavigationMenuItemCustomActionView.assertIsNotSelected();
        espNavigationMenuItemCustomActionView.click();
        espNavigationMenuItemCustomActionView.assertIsSelected();
        espTextViewClickFeedback.assertTextIs(navigationItemTitleCustomActionView);
    }

    @Test
    public void testClickFailureWhenInvisible() {
        exception.expect(NoMatchingViewException.class);
        exception.expectMessage("No views in hierarchy found matching: ((an instance of android.support.design.internal.NavigationMenuItemView and has child: with text: is \"standard navigation item\" and is displayed on the screen to the user) and is displayed on the screen to the user)");

        espNavigationMenuItemStandard.click();
    }

    @Test
    public void testCustomBaseMatcher() {
        espNavigationMenuItemStandard = new EspNavigationMenuItem(withText(navigationItemTitleStandard));
        espNavigationMenuItemStandard.assertIsHidden();
    }

    protected void givenSomeNavigationItems() {
        final TextView textViewClickFeedback = new TextView(activityTestRule.getActivity());
        textViewClickFeedback.setId(textViewClickFeedbackId);
        textViewClickFeedback.setText(initialClickFeedbackText);
        addViewToActivity(textViewClickFeedback, NavigationDrawerActivity.rootLayout);

        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                final NavigationView navigationView = (NavigationView) activityTestRule.getActivity().findViewById(NavigationDrawerActivity.drawerNavigationView);

                Menu menu = navigationView.getMenu();
                final MenuItem menuItemStandard = menu.add(GROUP_ID_STANDARD, 1, Menu.NONE, null);
                menuItemStandard.setTitle(navigationItemTitleStandard);
                menuItemStandard.setCheckable(true);

                final MenuItem menuItemCustom = menu.add(GROUP_ID_CUSTOM_ACTION_VIEW, 2, Menu.NONE, null);
                menuItemCustom.setCheckable(true);
                MenuItemCompat.setActionView(menuItemCustom, android.R.layout.simple_list_item_1);
                View actionView = MenuItemCompat.getActionView(menuItemCustom);
                TextView textView = (TextView) actionView.findViewById(android.R.id.text1);
                textView.setText(navigationItemTitleCustomActionView);

                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        if (item.getGroupId() == GROUP_ID_STANDARD) {
                            textViewClickFeedback.setText(item.getTitle());
                            return true;
                        }
                        if (item.getGroupId() == GROUP_ID_CUSTOM_ACTION_VIEW) {
                            View actionView = MenuItemCompat.getActionView(item);
                            TextView textView = (TextView) actionView.findViewById(android.R.id.text1);
                            textViewClickFeedback.setText(textView.getText());
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }
}