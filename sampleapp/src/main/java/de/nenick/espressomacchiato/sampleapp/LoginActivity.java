package de.nenick.espressomacchiato.sampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/** Dummy Login activity with only fail logic */
public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private EditText username;
    private Button confirmButton;
    private TextView errorMessage;

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // toggle only after text changed
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // toggle only after text changed
        }

        @Override
        public void afterTextChanged(Editable s) {
            toggleConfirmButton();
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            errorMessage.setText(R.string.error_invalid_username_password);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        confirmButton = (Button) findViewById(R.id.buttonConfirm);
        errorMessage = (TextView) findViewById(R.id.textViewErrorMessage);

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        confirmButton.setOnClickListener(clickListener);

        toggleConfirmButton();
    }

    private void toggleConfirmButton() {
        if (username.length() > 0 && password.length() > 0) {
            confirmButton.setEnabled(true);
        } else {
            confirmButton.setEnabled(false);
        }
    }
}

