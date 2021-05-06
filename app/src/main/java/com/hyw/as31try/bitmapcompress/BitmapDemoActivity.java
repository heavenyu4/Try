package com.hyw.as31try.bitmapcompress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hyw.as31try.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_demo);

        String s = downSample("/storage/sda1/图片/9622064_105642209176_2.jpg");
    }

    /**
     * U盘内容展示, 图片较大时, 下采样, 并配置白色背景
     * @param path 源图片全路径
     * @return 压缩后图片全路径
     */
    public static String downSample(String path){
        String newBitmapPath = "/sdcard/.kbar/udiskpic/temp.png";
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        Bitmap oriBitmap = BitmapFactory.decodeFile(path, option);
        int width = option.outWidth;
        int height = option.outHeight;

        int scaleWidth = width / 100;
        int scaleHeight = height / 50;
        if (scaleWidth > 1 && scaleHeight > 1){
            option.inSampleSize = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
            option.inJustDecodeBounds = false;
            option.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap newBitmap = BitmapFactory.decodeFile(path, option);
            saveBitmap(newBitmap, newBitmapPath);
            newBitmap.recycle();
//            oriBitmap.recycle();
            return newBitmapPath;
        }else{
            return path;
        }

    }

    /**
     * 保存方法
     */
    public static void saveBitmap(Bitmap bitmap, String QRpath) {
        Long time = System.currentTimeMillis();
        File f = new File(QRpath);
        // File f = new File(QRpath);
        String picturePath = f.getPath();
        if (f.exists()) {
            f.delete();
        }
        if (!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
