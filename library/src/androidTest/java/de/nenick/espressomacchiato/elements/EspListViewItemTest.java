package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.R;
import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressotools.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class EspListViewItemTest extends EspressoTestCase<LongListActivity> {

    private EspListViewItem espListViewItem = EspListViewItem.byText(LongListActivity.lastListItemText, LongListActivity.dataSourceTextColumn);

    @Test
    public void testByText() {
        espListViewItem.scrollTo();
        espListViewItem.assertIsVisible();
    }
}