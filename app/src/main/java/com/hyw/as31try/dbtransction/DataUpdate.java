package com.hyw.as31try.dbtransction;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hyw.as31try.utils.FileUtilsThird;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Author: heaven
 * Time: 2019/4/30  15:38
 * Description:
 */
public class DataUpdate {
String TAG = "DataUpdate";


    public boolean updateDatabases(String dbfile, String[] files) {
        SQLiteDatabase db = null;
        boolean ok = false;
        try {
            db = SQLiteDatabase.openDatabase(dbfile, null, SQLiteDatabase.OPEN_READWRITE);
            db.beginTransaction();;
            for (int m = 0; m < files.length; m++) {
                ok = updateDatabase(db,files[m]);
                if (ok) {
                    //成功时删除该文件
                    FileUtilsThird.deleteFile(files[m]);
                }else{
                    //失败时推出更新循环
                    break;
                }
            }
            if(ok){
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ok=false;
        } finally {
            db.endTransaction();//结束事务
            db.close();
        }
        return ok;
    }

    private boolean updateDatabase(SQLiteDatabase db, String sqlfile) {
        Log.d(TAG, "updateDatabase: " + sqlfile);
        boolean ok = false;
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(sqlfile));
            line = br.readLine();
            while (line != null) {
                if (line.length() > 0) {
                    // TODO 有时间得优化
                    db.execSQL(line);
                    Log.i(TAG, "updateDatabase: " + line);
                }
                line = br.readLine();
            }
            ok = true;
//            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "updateDatabase: " +line);
            e.printStackTrace();
            ok = false;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "updateDatabase: ", e);
            }
        }
        return ok;
    }

}
