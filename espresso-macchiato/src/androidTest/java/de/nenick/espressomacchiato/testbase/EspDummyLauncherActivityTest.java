package de.nenick.espressomacchiato.testbase;

import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspButton;
import de.nenick.espressomacchiato.elements.EspDevice;
import de.nenick.espressomacchiato.elements.EspPage;
import de.nenick.espressomacchiato.test.views.BaseActivity;

/** Basic test */
public class EspDummyLauncherActivityTest extends EspressoTestCase<EspDummyLauncherActivity> {

    @Test
    public void testDummyLauncher() {
        EspPage.byId(EspDummyLauncherActivity.rootLayout).assertIsDisplayedOnScreen();

        // test start method
        EspDummyLauncherActivity activity = getActivity();
        activity.setStartIntent(BaseActivity.class);
        activity.start();
        EspPage.byId(BaseActivity.rootLayout).assertIsDisplayedOnScreen();

        EspDevice.root().clickBackButton();

        EspPage.byId(EspDummyLauncherActivity.rootLayout).assertIsDisplayedOnScreen();

        // test click action
        EspButton.byId(EspDummyLauncherActivity.buttonId).click();
        EspPage.byId(BaseActivity.rootLayout).assertIsDisplayedOnScreen();
    }

}