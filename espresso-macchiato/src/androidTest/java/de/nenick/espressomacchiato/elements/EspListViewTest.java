package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.R;
import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class EspListViewTest extends EspressoTestCase<LongListActivity> {

    private EspListView espListView = EspListView.byId(R.id.list);

    @Test
    public void testById() {
        espListView.assertIsVisible();
    }

    @Test
    public void testTemplateConstructor() {
        espListView = new EspListView(espListView);
        espListView.assertIsVisible();
    }
}