package de.nenick.espressomacchiato.test.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.nenick.espressomacchiato.test.R;

/** Simple activity with just a root layout */
public class BaseActivity extends AppCompatActivity {

    public static int rootLayout = R.id.layout_activity_base;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
