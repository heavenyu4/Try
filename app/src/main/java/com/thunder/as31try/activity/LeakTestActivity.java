package com.thunder.as31try.activity;

import android.os.Bundle;

import com.thunder.as31try.BaseActivity;
import com.thunder.as31try.R;

public class LeakTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_test);
    }
}
