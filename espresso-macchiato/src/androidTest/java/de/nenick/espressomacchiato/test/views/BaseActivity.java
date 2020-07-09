package de.nenick.espressomacchiato.test.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.nenick.espressomacchiato.test.R;

/** Simple activity with just a root layout */
public class BaseActivity extends AppCompatActivity {

    public static int rootLayout = R.id.layout_activity_base;

    /** Simple create method */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
