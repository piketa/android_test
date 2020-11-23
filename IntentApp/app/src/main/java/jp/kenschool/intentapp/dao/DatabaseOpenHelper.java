package jp.kenschool.intentapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    // データメース名の指定
    private static final String DB_NAME = "attendance.db";

    // データベースのバージョン指定
    private static final int DB_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // テーブル作成
        sqLiteDatabase.execSQL("create table attendance(" +
                "_id integer primary key autoincrement not null, " +
                "name text not null, " +
                "attendance text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // データベースに変更が生じた場合の処理
    }
}
