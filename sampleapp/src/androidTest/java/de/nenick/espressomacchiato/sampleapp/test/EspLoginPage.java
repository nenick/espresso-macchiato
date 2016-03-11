package de.nenick.espressomacchiato.sampleapp.test;

import de.nenick.espressomacchiato.elements.EspButton;
import de.nenick.espressomacchiato.elements.EspEditText;
import de.nenick.espressomacchiato.elements.EspPage;
import de.nenick.espressomacchiato.elements.EspTextView;

import de.nenick.espressomacchiato.sampleapp.R;


public class EspLoginPage extends EspPage {

    public EspLoginPage() {
        super(R.id.layout_activity_login);
    }

    public EspButton confirm() {
        return new EspButton(R.id.buttonConfirm);
    }

    public EspEditText username() {
        return new EspEditText(R.id.editTextUsername);
    }

    public EspEditText password() {
        return new EspEditText(R.id.editTextPassword);
    }

    public EspTextView errorMessage() {
        return new EspTextView(R.id.textViewErrorMessage);
    }
}
