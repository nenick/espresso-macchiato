package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

public class EspPageTest extends EspressoTestCase<BaseActivity> {

    private EspPage espPage = EspPage.byId(BaseActivity.rootLayout);

    @Test
    public void testPage() {
        espPage.assertIsVisible();
    }

    @Test
    public void testCustomBaseMatcher() {
        espPage = new EspPage(allOf(isDisplayed(), withId(android.R.id.content)));
        espPage.assertIsVisible();
    }
}