package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspViewSwipeTest extends EspressoTestCase<LongListActivity> {

    private EspView espView = EspView.byId(LongListActivity.rootLayout);

    @Test
    public void testSwipeUp() {
        MyEspListView myEspListView = new MyEspListView(EspListView.byId(LongListActivity.listViewId));
        myEspListView.itemByVisibleIndex(0).textView().assertTextIs("item: 0");

        espView.swipeUp();

        myEspListView.itemByVisibleIndex(0).textView().assertTextIs("item: 12");
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