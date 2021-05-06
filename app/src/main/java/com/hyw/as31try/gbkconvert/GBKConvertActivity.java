package com.hyw.as31try.gbkconvert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hyw.as31try.R;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;

public class GBKConvertActivity extends AppCompatActivity {
    String TAG = "GBKConvertActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbkconvert);

        File file = new File("storage/sdb1/video");
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".rm")) {
                    return true;
                }
                return false;
            }
        });
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                String name = files[i].getName();
                Log.d(TAG, String.format("onCreate: name[%s] convert[%s]", name, convert2utf8(name)));
            }
        }
    }

    private String convert2utf8(String ori){
        try {
//            return new String(ori.getBytes("gbk"),"utf-8");
            return new String(ori.getBytes(),"GBK");
//            return ChangeCharset.getInstance().changeCharset(ori, "GBK");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "convert2utf8: ori: ", e);
            e.printStackTrace();
            return ori;
        }
    }
}
