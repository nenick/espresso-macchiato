package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.Map;

import de.nenick.espressomacchiato.matchers.EspAdapterViewItemMatcher;
import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;

public class EspAdapterViewItem extends EspView {

    private int adapterViewId;
    private int index;
    private Mode mode;
    //private String property;
    //private String itemText;

    public EspAdapterViewItem(EspAdapterViewItem template) {
        super(template);
        adapterViewId = template.adapterViewId;
        index = template.index;
        mode = template.mode;
    }

    enum Mode {
        byItemIndex,
        byVisibleIndex
    }

    @Deprecated
    public static EspAllOfBuilder<EspView> byAll() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static EspView byId(int resourceId) {
        throw new UnsupportedOperationException();
    }


    public static EspAdapterViewItem byItemIndex(int adapterViewId, int index) {
        return new EspAdapterViewItem(withId(adapterViewId), adapterViewId, index, Mode.byItemIndex);
    }

    public static EspAdapterViewItem byVisibleIndex(int adapterViewId, int index) {
        return new EspAdapterViewItem(EspAdapterViewItemMatcher.withAdapterView(withId(adapterViewId)).atChildIndex(index), adapterViewId, index, Mode.byVisibleIndex);
    }

    /*
        public static EspAdapterViewItem byText(String text, String property) {
            return new EspAdapterViewItem(text, property);
        }

        public EspAdapterViewItem(String itemText, String dataSourceField) {
            super(withText(itemText));
            this.itemText = itemText;
            this.property = dataSourceField;
        }
    */
    public EspAdapterViewItem(Matcher<View> base, int adapterViewId, int index, Mode mode) {
        super(base);
        this.adapterViewId = adapterViewId;
        this.index = index;
        this.mode = mode;
    }

    public void scrollTo() {
        if (mode == Mode.byItemIndex) {
            onData(instanceOf(Map.class)).atPosition(index).check(matches(ViewMatchers.isCompletelyDisplayed()));
            //onRow(itemText, property).check(matches(isCompletelyDisplayed()));
        } else {
            throw new UnsupportedOperationException("Method only supported when item accessed byItemIndex");
        }
    }
/*
    protected DataInteraction onRow(Object value, String property) {
        return onData(hasEntry(equalTo(property), is(value)));
    }
*/
    protected Matcher<View> baseMatcherForItemChild(final Matcher<View> childMatcher) {
        if (mode == Mode.byVisibleIndex) {
            return EspAdapterViewItemMatcher.withAdapterView(withId(adapterViewId)).atChildIndexOnView(index, childMatcher);
        } else {
            throw new UnsupportedOperationException("Method only supported when item accessed byVisibleIndex");
        }
    }
}
