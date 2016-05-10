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
    public void testCustomBaseMatcher() {
        //espListView = new EspListView(allOf(withId(R.id.list), isDisplayed()));
        espListView.assertIsVisible();
    }

    /*

    @Test // optional
    public void toggle_Click() {
        // Click on a toggle button.
        onRow(TEXT_ITEM_30).onChildView(withId(R.id.rowToggleButton)).perform(click());

        // Check that the toggle button is checked.
        onRow(TEXT_ITEM_30).onChildView(withId(R.id.rowToggleButton)).check(matches(isChecked()));
    }


     //Make sure that clicking on the toggle button doesn't trigger a click on the row.

    @Test // optional
    public void toggle_ClickDoesntPropagate() {
        // Click on one of the rows.
        onRow(TEXT_ITEM_30).onChildView(withId(R.id.rowContentTextView)).perform(click());

        // Click on the toggle button, in a different row.
        onRow(TEXT_ITEM_60).onChildView(withId(R.id.rowToggleButton)).perform(click());

        // Check that the activity didn't detect the click on the first column.
        onView(ViewMatchers.withId(R.id.selection_row_value))
                .check(matches(withText(TEXT_ITEM_30_SELECTED)));
    }

    */
}