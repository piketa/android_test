package jp.kenschool.myschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

/**
 * スケジュールDAO
 */
public class ScheduleDao {

    public static final String TABLE_NAME = "schedule";
    public static final String SCHEDULE_ID = "schedule_id";
    public static final String SCHEDULE_DATE = "schedule_date";
    public static final String SCHEDULE_TIME = "schedule_time";
    public static final String SCHEDULE_TITLE = "schedule_title";
    public static final String SCHEDULE_DETAIL = "schedule_detail";


    private ContentValues createContentValues(ScheduleModel schedule) {
        ContentValues contentValues = new ContentValues();
        if (schedule.scheduleId != null) {
            contentValues.put(SCHEDULE_ID, schedule.scheduleId);
        }
        contentValues.put(SCHEDULE_DATE, schedule.date);
        contentValues.put(SCHEDULE_TIME, schedule.time);
        contentValues.put(SCHEDULE_TITLE, schedule.title);
        contentValues.put(SCHEDULE_DETAIL, schedule.detail);

        return contentValues;
    }

    public long insert(Context context, ScheduleModel schedule) {
        ScheduleDbHelper dbHelper = new ScheduleDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(TABLE_NAME, null, createContentValues(schedule));
    }
}
