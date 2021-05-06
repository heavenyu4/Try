package com.hyw.as31try.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.hyw.as31try.BaseActivity;
import com.hyw.as31try.R;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 查找硬盘下的文件 的真实后缀
 * 这样方便查找硬盘里的LS和MP3歌曲
 * 更新到数据库中
 */
public class DbUpdateActivity extends BaseActivity {

    private final Set<Integer> sLsSongs = Collections.synchronizedSet(new HashSet<Integer>());
    private final Set<Integer> sTsSongs = Collections.synchronizedSet(new HashSet<Integer>());
    private final Set<Integer> sMp3Songs = Collections.synchronizedSet(new HashSet<Integer>());
    private final Set<Integer> sWavSongs = Collections.synchronizedSet(new HashSet<Integer>());
    private final Set<Integer> sMpgSongs = Collections.synchronizedSet(new HashSet<Integer>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_update);

        new Thread(new Runnable() {
            @Override
            public void run() {
                updateDb();
            }
        }).start();
    }

    private void updateDb() {

        DBHelper helper = new DBHelper(this, "/sdcard/.kbar/db/media_info.sqlite",
                null, 220117);
        SQLiteDatabase database = helper.getWritableDatabase();
        database.beginTransaction();

        helper.resetPath();
        scan();

        Iterator<Integer> iterator = sLsSongs.iterator();
        while (iterator.hasNext()) {
            Integer no = iterator.next();
            helper.update(no, no + ".ls");
        }

//        iterator = sTsSongs.iterator();
//        while (iterator.hasNext()) {
//            Integer no = iterator.next();
//            helper.update(no, no + ".ts");
//        }

        iterator = sMp3Songs.iterator();
        while (iterator.hasNext()) {
            Integer no = iterator.next();
            helper.update(no, no + ".mp3");
        }
        iterator = sMpgSongs.iterator();
        while (iterator.hasNext()) {
            Integer no = iterator.next();
            helper.update(no, no + ".mpg");
        }
        iterator = sWavSongs.iterator();
        while (iterator.hasNext()) {
            Integer no = iterator.next();
            helper.update(no, no + ".wav");
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public synchronized void scan() {
        Log.d(TAG, "scan started!");
        long start = SystemClock.uptimeMillis();
        String videoPath = "/storage/sda1/video";
        if (StringUtils.isEmpty(videoPath)) {
            Log.e(TAG, "video path is empty!");
            return;
        }

        File videoDir = new File(videoPath);
        String[] allFiles = videoDir.list();
        int count = 0;
        for (String file : allFiles) {
            if (file.endsWith(".ts") || file.endsWith(".ls") || file.endsWith(".mp3") || file.endsWith(".wav") || file.endsWith(".mpg")) {
                int no = Integer.parseInt(file.substring(0, file.lastIndexOf(".")));
                if (no > 0) {
                    ++count;

                    if (file.endsWith(".ts")) {
                        sTsSongs.add(no);
                    } else if (file.endsWith(".ls")) {
                        sLsSongs.add(no);
                    } else if (file.endsWith(".mp3")) {
                        sMp3Songs.add(no);
                    } else if (file.endsWith(".wav")) {
                        sWavSongs.add(no);
                    } else if (file.endsWith(".mpg")) {
                        sMpgSongs.add(no);
                    }
                }
            }
        }
        Log.d(TAG, "scanning finished, add records: " + count + ", time used: " + (SystemClock.uptimeMillis() - start) + " ms");
    }
}
