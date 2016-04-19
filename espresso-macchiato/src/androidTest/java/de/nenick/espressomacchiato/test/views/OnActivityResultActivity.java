package de.nenick.espressomacchiato.test.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import de.nenick.espressomacchiato.test.R;

public class OnActivityResultActivity extends AppCompatActivity {

    public static int requestCodeResource = R.id.requestCode;
    public static int resultCodeResource = R.id.resultCode;
    public static int dataResource = R.id.data;

    private TextView requestCodeView;
    private TextView resultCodeView;
    private TextView dataView;
    private OnActivityResultListener listener;

    public interface OnActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    public void setListener(OnActivityResultListener listener) {
        this.listener = listener;
    }

    public void setDataViewText(String text) {
        dataView.setText(text);
    }

    public void startForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_activity_result);

        requestCodeView = (TextView) findViewById(requestCodeResource);
        resultCodeView = (TextView) findViewById(resultCodeResource);
        dataView = (TextView) findViewById(dataResource);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        requestCodeView.setText(String.valueOf(requestCode));
        resultCodeView.setText(String.valueOf(resultCode));
        dataView.setText(String.valueOf(data));
        if (listener != null) {
            listener.onActivityResult(requestCode, resultCode, data);
        }
    }
}
