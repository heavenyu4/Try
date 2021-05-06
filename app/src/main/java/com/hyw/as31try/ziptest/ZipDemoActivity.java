package com.hyw.as31try.ziptest;

import android.os.Bundle;
import android.os.Environment;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ZipUtils;
import com.hyw.as31try.BaseActivity;
import com.hyw.as31try.R;
import com.hyw.as31try.utils.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ZipDemoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_demo);

        initData();
    }

    private void initData() {
        String filePath = Environment.getExternalStorageDirectory() + "/update/jingyan.zip";
        Logger.error(TAG, "initData: filePath: " + filePath);
        try {
            InputStream is = getAssets().open("jingyan.zip");
            FileUtils.createOrExistsFile(filePath);
            FileOutputStream fos = new FileOutputStream(filePath);
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = is.read(buffer, 0, 4096)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            is.close();

            String destDirPath = Environment.getExternalStorageDirectory() + "/update/temp";
            Logger.error(TAG, "initData: destDirPath : " + destDirPath);
            FileUtils.deleteAllInDir(destDirPath+"/jingyan");
            ZipUtils.unzipFile(filePath, destDirPath);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
