package com.hyw.as31try.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Author: heaven
 * Time: 2019/9/5  14:44
 * Description:
 */
public class DBHelper extends SQLiteOpenHelper {
    String TAG = "DBHelper";
    final String HOT_TABLE_NAME = "song_hot_local";
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void update(int songno, String path){
        SQLiteDatabase database = getWritableDatabase();
        if (database!=null){
            String sql = String.format("update song_hot_local set songPath='%s' where songID=%s", path, songno);
            Log.d(TAG, "update: "+sql);
            database.execSQL(sql);
        }
    }

    public void resetPath(){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "update song_hot_local set songPath='' where 1=1";
        Log.d(TAG, "resetPath: " + sql);
        database.execSQL(sql);
    }

}
