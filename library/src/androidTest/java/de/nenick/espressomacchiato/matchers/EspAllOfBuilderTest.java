package de.nenick.espressomacchiato.matchers;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspView;
import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

@SuppressWarnings("ResourceType")
public class EspAllOfBuilderTest extends EspressoTestCase<BaseActivity> {

    public static final int TEST_PARENT_ID = 10;
    public static final int TEST_MID_ID = 100;
    public static final int TEST_ID = 1000;

    private Context context;
    private EspAllOfBuilder<EspView> builder = new EspAllOfBuilder<EspView>() {};

    @Before
    public void setup() {
        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void testWithId() {
        View view = new View(context);
        view.setId(TEST_ID);
        addViewToActivity(view, BaseActivity.rootLayout);

        builder.withId(TEST_ID);
        builder.build().assertIsDisplayedOnScreen();
    }

    @Test
    public void testWithParentInHierarchy() {
        // hierarchy A
        ViewGroup viewGroupParentA = new LinearLayout(context);
        viewGroupParentA.setId(TEST_PARENT_ID);
        addViewToActivity(viewGroupParentA, BaseActivity.rootLayout);

        ViewGroup viewGroupMidA = new LinearLayout(context);
        viewGroupMidA.setId(TEST_MID_ID);
        addViewToActivity(viewGroupMidA, TEST_PARENT_ID);

        // hierarchy B
        ViewGroup viewGroupParentB = new LinearLayout(context);
        viewGroupParentB.setId(TEST_PARENT_ID + 1);
        addViewToActivity(viewGroupParentB, BaseActivity.rootLayout);

        ViewGroup viewGroupMidB = new LinearLayout(context);
        viewGroupMidB.setId(TEST_MID_ID + 1);
        addViewToActivity(viewGroupMidB, TEST_PARENT_ID + 1);

        // view with same id on both hierarchies
        View viewSameIdA = new View(context);
        View viewSameIdB = new View(context);

        viewSameIdA.setId(TEST_ID);
        viewSameIdB.setId(TEST_ID);

        addViewToActivity(viewSameIdA, TEST_MID_ID);
        addViewToActivity(viewSameIdB, TEST_MID_ID + 1);

        builder.withId(TEST_ID).withParentInHierarchy(TEST_PARENT_ID).build().assertIsDisplayedOnScreen();
    }
}