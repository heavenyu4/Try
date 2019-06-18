package com.thunder.as31try.fileseq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thunder.as31try.R;

public class FileSequentialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_sequential);

        FileSequential fileSequential = new FileSequential();
        fileSequential.init();
    }
}
