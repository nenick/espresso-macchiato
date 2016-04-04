package de.nenick.espressomacchiato.testbase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.view.View;

import de.nenick.espressomacchiato.R;

public class EspDummyLauncherActivity extends Activity {

    public static int rootLayout = R.id.layout_activity_dummy_launcher;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_launcher);
        findViewById(R.id.btn_start_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    public void setStartIntent(Class<? extends Activity> activity) {
       setStartIntent(new Intent(InstrumentationRegistry.getTargetContext(), activity));
    }

    public void setStartIntent(Intent intent) {
        this.intent = intent;
    }

    public void start() {
        startActivity(intent);
    }
}
