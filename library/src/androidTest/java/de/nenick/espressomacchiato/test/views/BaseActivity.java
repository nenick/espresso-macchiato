package de.nenick.espressomacchiato.test.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import de.nenick.espressomacchiato.test.R;

public class BaseActivity extends AppCompatActivity {

    public static int linearLayout = R.id.mainLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
    }
}
