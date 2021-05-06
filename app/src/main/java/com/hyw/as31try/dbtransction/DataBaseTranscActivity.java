package com.hyw.as31try.dbtransction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hyw.as31try.R;

public class DataBaseTranscActivity extends AppCompatActivity {
    String TAG = "DataBaseTranscActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_transc);

        DataUpdate update = new DataUpdate();
        boolean result = update.updateDatabases("/sdcard/.kbar/db/media_info.sqlite",
                new String[]{"/sdcard/.kbar/db/del2.sql", "/sdcard/.kbar/db/del.sql"});
        Log.d(TAG, "onCreate: " + result);
    }
}
