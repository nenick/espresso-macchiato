package de.nenick.espressomacchiato.sampleapp.test;

import de.nenick.espressomacchiato.elements.basics.EspButton;
import de.nenick.espressomacchiato.elements.basics.EspEditText;
import de.nenick.espressomacchiato.elements.basics.EspTextView;
import de.nenick.espressomacchiato.elements.basics.EspView;
import de.nenick.espressomacchiato.sampleapp.R;

/** Sample test page object */
public class EspLoginPage extends EspView {

    public EspLoginPage() {
        super(R.id.layout_activity_login, null);
    }

    public EspButton confirm() {
        return new EspButton(R.id.confirm);
    }

    public EspEditText username() {
        return new EspEditText(R.id.username);
    }

    public EspEditText password() {
        return new EspEditText(R.id.password);
    }

    public EspTextView errorMessage() {
        return new EspTextView(R.id.textViewErrorMessage);
    }
}
