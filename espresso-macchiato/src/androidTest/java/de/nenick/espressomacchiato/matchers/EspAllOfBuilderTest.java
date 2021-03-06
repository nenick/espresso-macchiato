package de.nenick.espressomacchiato.matchers;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspDrawer;
import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.hamcrest.CoreMatchers.containsString;

/** Basic test */
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
        addViewToLayout(view, BaseActivity.rootLayout);

        builder.withId(TEST_ID);
        builder.build().assertIsDisplayedOnScreen();
    }

    @Test
    public void testWithParentInHierarchy() {
        // hierarchy A
        ViewGroup viewGroupParentA = new LinearLayout(context);
        viewGroupParentA.setId(TEST_PARENT_ID);
        addViewToLayout(viewGroupParentA, BaseActivity.rootLayout);

        ViewGroup viewGroupMidA = new LinearLayout(context);
        viewGroupMidA.setId(TEST_MID_ID);
        addViewToLayout(viewGroupMidA, TEST_PARENT_ID);

        // hierarchy B
        ViewGroup viewGroupParentB = new LinearLayout(context);
        viewGroupParentB.setId(TEST_PARENT_ID + 1);
        addViewToLayout(viewGroupParentB, BaseActivity.rootLayout);

        ViewGroup viewGroupMidB = new LinearLayout(context);
        viewGroupMidB.setId(TEST_MID_ID + 1);
        addViewToLayout(viewGroupMidB, TEST_PARENT_ID + 1);

        // view with same id on both hierarchies
        View viewSameIdA = new View(context);
        View viewSameIdB = new View(context);

        viewSameIdA.setId(TEST_ID);
        viewSameIdB.setId(TEST_ID);

        addViewToLayout(viewSameIdA, TEST_MID_ID);
        addViewToLayout(viewSameIdB, TEST_MID_ID + 1);

        builder.withId(TEST_ID).withParentInHierarchy(TEST_PARENT_ID).build().assertIsDisplayedOnScreen();
    }

    @Test
    public void testBuildFailure() {
        EspAllOfBuilder unknownBuilder = new EspAllOfBuilder() {};

        exception.expect(IllegalStateException.class);
        exception.expectMessage("java.lang.IllegalArgumentException: Please provide generic element");
        unknownBuilder.build();
    }

    @Test
    public void testBuildFailureNoMatchingConstructorFound() {
        EspAllOfBuilder<EspDrawer> drawerBuilder = new EspAllOfBuilder<EspDrawer>() {};

        exception.expect(IllegalStateException.class);
        exception.expectMessage(containsString("java.lang.NoSuchMethodException:"));
        drawerBuilder.build();
    }
}
