package de.nenick.espressomacchiato.test.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.nenick.espressomacchiato.test.R;

/** Simple activity but with fixed orientation. */
public class LandscapeFixedActivity extends AppCompatActivity {

    public static int rootLayout = R.id.layout_activity_landscape_fixed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(de.nenick.espressomacchiato.test.R.layout.activity_landscape_fixed);
    }
}
