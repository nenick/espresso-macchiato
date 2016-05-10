package de.nenick.espressomacchiato.elements;

import junit.framework.AssertionFailedError;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspViewListSupportedTest extends EspressoTestCase<LongListActivity> {

    private EspPage espPage = EspPage.byId(LongListActivity.rootLayout);

    @Test
    public void testSwipe() {
        EspTextView.byText("item: 0").assertIsDisplayedOnScreen();

        espPage.swipeUp();
        EspTextView.byText("item: 0").assertNotExist();

        espPage.swipeDown();
        espPage.swipeDown();
        EspTextView.byText("item: 0").assertIsDisplayedOnScreen();
    }

    @Test
    public void testAssertIsHiddenFailureWhenOnlyPartialHidden() {
        getActivity().findViewById(LongListActivity.listViewId).scrollBy(0, 150);
        EspTextView.byText("item: 0").assertIsHidden();

        getActivity().findViewById(LongListActivity.listViewId).scrollBy(0, -70);
        exception.expect(AssertionFailedError.class);
        EspTextView.byText("item: 0").assertIsHidden();
    }

    @Test
    public void testAssertIsDisplayedOnScreenFailureWhenOnlyPartialHidden() {
        getActivity().findViewById(LongListActivity.listViewId).scrollBy(0, 70);

        exception.expect(AssertionFailedError.class);
        EspTextView.byText("item: 0").assertIsDisplayedOnScreen();
    }

    class MyEspListView extends EspListView {

        public MyEspListView(EspListView template) {
            super(template);
        }

        @Override
        public MyEspAdapterViewItem itemByVisibleIndex(int index) {
            return new MyEspAdapterViewItem(super.itemByVisibleIndex(index));
        }
    }

    class MyEspAdapterViewItem extends EspAdapterViewItem {

        public MyEspAdapterViewItem(EspAdapterViewItem template) {
            super(template);
        }

        public EspTextView textView() {
            return new EspTextView(baseMatcherForItemChild(withId(LongListActivity.itemTextViewId)));
        }
    }
}