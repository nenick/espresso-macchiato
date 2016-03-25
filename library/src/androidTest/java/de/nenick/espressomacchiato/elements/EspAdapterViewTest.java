package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressotools.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class EspAdapterViewTest extends EspressoTestCase<LongListActivity> {

    private EspAdapterView espAdapterView = EspAdapterView.byId(LongListActivity.listViewId);

    @Test
    public void testById() {
        espAdapterView.assertIsVisible();
    }

    @Test
    public void testCustomBaseMatcher() {
        espAdapterView = new EspAdapterView(allOf(withId(LongListActivity.listViewId), isDisplayed()));
        espAdapterView.assertIsVisible();
    }

    @Test
    public void testAssertItemCountIs() {
        espAdapterView.assertItemCountIs(LongListActivity.listViewItemCount);
    }
}