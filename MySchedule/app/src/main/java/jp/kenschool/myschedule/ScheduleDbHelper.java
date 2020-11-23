package jp.kenschool.myschedule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * データベース接続クラス.
 */
public class ScheduleDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myschedule.db";

    public ScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String comma = ",";
        String textType = " TEXT";
        String sqlCreateEntries =
                "CREATE TABLE " + ScheduleDao.TABLE_NAME + "(" +
                        ScheduleDao.SCHEDULE_ID + " INTEGER PRIMARY KEY" +
                        comma + ScheduleDao.SCHEDULE_DATE + textType +
                        comma + ScheduleDao.SCHEDULE_TIME + textType +
                        comma + ScheduleDao.SCHEDULE_TITLE + textType +
                        comma + ScheduleDao.SCHEDULE_DETAIL + textType +
                        ")";
        db.execSQL(sqlCreateEntries);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //データベースの変更が生じた場合は、ここに処理を記述する
    }
}
