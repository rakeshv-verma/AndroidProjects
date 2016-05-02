package com.example.android.againfileexp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends Activity {
    private UIView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mView = (UIView) getFragmentManager().findFragmentById(R.id.file_list);
    }

    @Override
    public void onBackPressed() {
        mView.goBack();


    }
}
