package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.matchers.EspAdapterViewItemMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspListViewItem extends EspAdapterViewItem {

    public static EspListViewItem byItemIndex(int adapterViewId, int index) {
        return new EspListViewItem(withId(adapterViewId), adapterViewId, index, Mode.byItemIndex);
    }

    public static EspListViewItem byVisibleIndex(int adapterViewId, int index) {
        return new EspListViewItem(EspAdapterViewItemMatcher.withAdapterView(withId(adapterViewId)).atChildIndex(index), adapterViewId, index, Mode.byVisibleIndex);
    }

    public EspListViewItem(EspAdapterViewItem template) {
        super(template);
    }

    public EspListViewItem(Matcher<View> listMatcher, int listViewId, int index, Mode mode) {
        super(listMatcher, listViewId, index, mode);
    }

/*
    public static EspListViewItem byText(String text, String dataSourceField) {
        return new EspListViewItem(text, dataSourceField);
    }

    public EspListViewItem(String itemText, String dataSourceField) {
        super(itemText, dataSourceField);
    }
*/
}
