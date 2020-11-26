package jp.kenschool.mycalendar.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * スケジュール用データベースオープンヘルパー
 */

public class ScheduleDbOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "schedule.db";

    public ScheduleDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String comma = ",";
        String textType = " TEXT";
        String sqlCreateEntries = "CREATE TABLE " +
                ScheduleDao.TABLE_NAME + "(" +
                ScheduleDao.ID + " INTEGER PRIMARY KEY" +
                comma + ScheduleDao.SCHEDULE_DATE + textType +
                comma + ScheduleDao.SCHEDULE_TIME + textType +
                comma + ScheduleDao.SCHEDULE_TITLE + textType +
                comma + ScheduleDao.SCHEDULE_DETAIL + textType +
                ")";
        db.execSQL(sqlCreateEntries);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
