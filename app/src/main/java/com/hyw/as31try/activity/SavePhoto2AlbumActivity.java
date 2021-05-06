package com.hyw.as31try.activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ImageUtils;
import com.hyw.as31try.R;

public class SavePhoto2AlbumActivity extends AppCompatActivity {
    private String TAG = "SavePhoto2AlbumActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_photo2_album);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = findViewById(R.id.imageView);
                Bitmap bitmap = ImageUtils.getBitmap(R.drawable.common_flash);
                Log.d(TAG, "onClick: bitmap: " + bitmap);
                ImageUtils.save2Album(bitmap, Bitmap.CompressFormat.PNG);
            }
        });
    }
}