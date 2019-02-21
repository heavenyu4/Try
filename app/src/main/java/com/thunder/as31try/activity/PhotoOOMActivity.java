package com.thunder.as31try.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thunder.as31try.R;

public class PhotoOOMActivity extends Activity {

    private LinearLayout llContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_oom);

        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/dump/oom_test.jpg");
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(bitmap);
            llContainer.addView(iv);
        }
    }

    private void initView() {
        llContainer = ((LinearLayout) findViewById(R.id.ll_container));
    }
}
