package com.hyw.as31try.service;

import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;
import com.hyw.as31try.utils.FileUtilsThird;
import com.hyw.as31try.utils.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hyw on 2018/4/12.
 */

public class LeakUploadService extends DisplayLeakService {
    String TAG = getClass().getSimpleName();

    @Override
    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
        super.afterDefaultHandling(heapDump, result, leakInfo);
        Logger.error(TAG, "afterDefaultHandling: ");
        if (!result.leakFound || result.excludedLeak) {
            return;
        }

        Logger.error(TAG, "afterDefaultHandling: " + leakInfo);
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String timeDesc = sdf.format(time);
        String localFileName = "sdcard/leak/" + timeDesc + "-leak";
        File localFile = new File(localFileName);
        FileUtilsThird.createOrExistsFile(localFile);
        Logger.error(TAG, "afterDefaultHandling: save file " + localFileName);
        Logger.error(TAG, "afterDefaultHandling: heapDump.heapDumpFile: "+heapDump.heapDumpFile.getAbsolutePath());
        FileUtilsThird.copyFile(heapDump.heapDumpFile, localFile, new FileUtilsThird.OnReplaceListener() {
            @Override
            public boolean onReplace() {
                return true;
            }
        });
    }
}
