package com.kuro.musicplayer.Sqlite;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String TAG = "-----SQLiteHelper";

    private static final String CREATE_TABLE = "create table music ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "musician text, "
            + "path text, "
            + "image_path integer)";//数据库里的表

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
        Log.i(TAG, "Constructor invoked");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            Log.i(TAG, "数据库创建成功");
        } catch (SQLException s) {
            Log.i(TAG, "数据库创建失败");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
