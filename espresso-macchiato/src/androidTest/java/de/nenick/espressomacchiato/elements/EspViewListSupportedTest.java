package de.nenick.espressomacchiato.elements;

import android.support.test.InstrumentationRegistry;

import junit.framework.AssertionFailedError;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspViewListSupportedTest extends EspressoTestCase<LongListActivity> {

    private EspPage espPage = EspPage.byId(LongListActivity.rootLayout);
    private EspTextView firstItem = EspTextView.byText("item: 0");

    @Test
    public void testSwipe() {
        firstItem.assertIsDisplayedOnScreen();

        espPage.swipeUp();
        firstItem.assertNotExist();

        espPage.swipeDown();
        espPage.swipeDown();
        firstItem.assertIsDisplayedOnScreen();
    }

    @Test
    public void testAssertIsHiddenFailureWhenOnlyPartialHidden() {
        scrollListPixelDistance(100);
        firstItem.assertIsHidden();

        scrollListPixelDistance(-80);
        firstItem.assertIsPartiallyDisplayedOnly();

        exception.expect(AssertionFailedError.class);
        firstItem.assertIsHidden();
    }

    @Test
    public void testAssertIsDisplayedOnScreenFailureWhenOnlyPartialHidden() {
        scrollListPixelDistance(100);

        exception.expect(AssertionFailedError.class);
        firstItem.assertIsDisplayedOnScreen();
    }

    private void scrollListPixelDistance(final int distance) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getActivity().findViewById(LongListActivity.listViewId).scrollBy(0, distance);
            }
        });
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