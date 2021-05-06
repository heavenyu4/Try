package com.hyw.as31try.activity;

import android.os.Bundle;

import com.hyw.as31try.BaseActivity;
import com.hyw.as31try.R;

public class LeakTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_test);
    }
}
