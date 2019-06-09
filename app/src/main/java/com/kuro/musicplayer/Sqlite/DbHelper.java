package com.kuro.musicplayer.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.kuro.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O_MR1)
public class DbHelper {

    private static volatile DbHelper helper;
    private SqliteHelper sqliteHelper;
    public static final String DATABASE_NAME = "music_store.db";//数据库名字
    private static final int DATABASE_VERSION = 1;//数据库版本号

    private DbHelper(Context context) {
        sqliteHelper = new SqliteHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHelper setDatabase(Context context) {
        if (helper == null) {
            synchronized (DbHelper.class) {
                if (helper == null) {
                    helper = new DbHelper(context);
                }
            }
        }
        return helper;
    }

    private SQLiteDatabase getReader() {
        return sqliteHelper.getReadableDatabase();
    }

    private SQLiteDatabase getWriter() {
        return sqliteHelper.getWritableDatabase();
    }

    public List<Music> getAllMusic() {
        String sql = "select * from music";
        List<Music> music = new ArrayList<Music>();
        Cursor cursor = getReader().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Music m = new Music();
            m.setId(cursor.getInt(cursor.getColumnIndex("id")));
            m.setMusicName(cursor.getString(cursor.getColumnIndex("name")));
            m.setMusician(cursor.getString(cursor.getColumnIndex("musician")));
            m.setPath(cursor.getString(cursor.getColumnIndex("path")));
            m.setNetImagePath(cursor.getString(cursor.getColumnIndex("net_path")));
            m.setImagePath(cursor.getInt(cursor.getColumnIndex("image_path")));
            m.setType(cursor.getInt(cursor.getColumnIndex("type")));
            m.setnId(cursor.getInt(cursor.getColumnIndex("n_id")));
            music.add(m);
        }
        cursor.close();
        return music;
    }

    public List<Music> getAllMusicByType(int type) {
        String sql = "select * from music where type = ?";
        List<Music> music = new ArrayList<Music>();
        Cursor cursor = getReader().rawQuery(sql, new String[] {String.valueOf(type)});
        while (cursor.moveToNext()) {
            Music m = new Music();
            m.setId(cursor.getInt(cursor.getColumnIndex("id")));
            m.setMusicName(cursor.getString(cursor.getColumnIndex("name")));
            m.setMusician(cursor.getString(cursor.getColumnIndex("musician")));
            m.setPath(cursor.getString(cursor.getColumnIndex("path")));
            m.setNetImagePath(cursor.getString(cursor.getColumnIndex("net_path")));
            m.setImagePath(cursor.getInt(cursor.getColumnIndex("image_path")));
            m.setType(cursor.getInt(cursor.getColumnIndex("type")));
            m.setnId(cursor.getInt(cursor.getColumnIndex("n_id")));
            music.add(m);
        }
        cursor.close();
        return music;
    }

    public void addMusic(Music music) {
        String sql = "insert into music (id, name, musician, path, net_path, image_path, type, n_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        getWriter().execSQL(sql, new Object[] {music.getId(), music.getMusicName(), music.getMusician()
                        , music.getPath(), music.getNetImagePath(), music.getImagePath()
                        , music.getType(), music.getnId()});
    }

    public void addMusicList(List<Music> musics) {
        for (Music m : musics) {
            addMusic(m);
        }
    }

    public void deleteMusicById(int id) {
        String sql = "delete from music where id = ?";
        getWriter().execSQL(sql, new Object[] {id});
    }

    public void deleteAllMusics() {
        getWriter().execSQL("delete from music");
    }

    public void createDatabase() {
        getWriter().close();
    }
}
