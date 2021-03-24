package com.example.esp_android_material;


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matchers;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.CollapsingToolbarActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Basic tests
 */
public class EspAppBarLayoutTest extends EspressoTestCase<CollapsingToolbarActivity> {

    @Test
    public void testCreateByAll() {
        EspAppBarLayout espAppBarLayout = EspAppBarLayout.byAll().withId(CollapsingToolbarActivity.appBarLayoutId).build();
        espAppBarLayout.assertIsExpanded();
    }

    @Test
    public void testTemplateConstructor() {
        EspAppBarLayout template = EspAppBarLayout.byId(CollapsingToolbarActivity.appBarLayoutId);
        EspAppBarLayout espAppBarLayout = new EspAppBarLayout(template);
        espAppBarLayout.assertIsExpanded();
    }

    @Test
    public void testBaseConstructor() {
        EspAppBarLayout espAppBarLayout = new EspAppBarLayout(ViewMatchers.withId(CollapsingToolbarActivity.appBarLayoutId));
        espAppBarLayout.assertIsExpanded();
    }

    @Test
    public void testCollapseAndExpand() {
        EspAppBarLayout espAppBarLayout = EspAppBarLayout.byId(CollapsingToolbarActivity.appBarLayoutId);
        espAppBarLayout.assertIsExpanded();
        espAppBarLayout.collapse();
        espAppBarLayout.assertIsCollapsed();
        espAppBarLayout.expand();

        exception.expect(AssertionFailedError.class);
        exception.expectMessage(Matchers.containsString("is full collapsed"));
        exception.expectMessage(Matchers.containsString("Got: <false>"));
        espAppBarLayout.assertIsCollapsed();
    }

    @Test
    public void testAssertIsCollapsedFailure() {
        EspAppBarLayout espAppBarLayout = EspAppBarLayout.byId(CollapsingToolbarActivity.appBarLayoutId);

        exception.expect(AssertionFailedError.class);
        exception.expectMessage(Matchers.containsString("is full collapsed"));
        exception.expectMessage(Matchers.containsString("Got: <false>"));
        espAppBarLayout.assertIsCollapsed();
    }

    @Test
    public void testAssertIsExpandedFailure() {
        EspAppBarLayout espAppBarLayout = EspAppBarLayout.byId(CollapsingToolbarActivity.appBarLayoutId);
        espAppBarLayout.collapse();

        exception.expect(AssertionFailedError.class);
        exception.expectMessage(Matchers.containsString("is full expanded"));
        exception.expectMessage(Matchers.containsString("Got: <false>"));
        espAppBarLayout.assertIsExpanded();
    }

    @Test
    public void testAssertIsCollapsedNeedsCoordinatorLayout() {
        EspAppBarLayout espAppBarLayout = EspAppBarLayout.byId(CollapsingToolbarActivity.appBarLayoutId);
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((AppBarLayout) getActivity().findViewById(CollapsingToolbarActivity.appBarLayoutId)).removeAllViews();
            }
        });


        exception.expect(IllegalStateException.class);
        exception.expectMessage("Current only AppBarLayouts with CollapsingToolbarLayout are supported.");
        espAppBarLayout.assertIsCollapsed();
    }

    @Test
    public void testAssertIsCollapsedNeedsToolbar() {
        EspAppBarLayout espAppBarLayout = EspAppBarLayout.byId(CollapsingToolbarActivity.appBarLayoutId);
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((CollapsingToolbarLayout) getActivity().findViewById(CollapsingToolbarActivity.collapseLayoutId)).removeAllViews();
            }
        });

        exception.expect(IllegalStateException.class);
        exception.expectMessage("Current only CollapsingToolbarLayout with support Toolbar are supported.");
        espAppBarLayout.assertIsCollapsed();
    }
}