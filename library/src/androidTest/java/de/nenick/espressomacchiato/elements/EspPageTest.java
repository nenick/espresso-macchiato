package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspPageTest extends EspressoTestCase<BaseActivity> {

    private EspPage espPage = EspPage.byId(BaseActivity.rootLayout);

    @Test
    public void testPage() {
        espPage.assertIsVisible();
    }
}