package jp.kenschool.mycalendar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import jp.kenschool.mycalendar.dto.ScheduleDto;

/**
 * スケジュールDAO
 */

public class ScheduleDao {
    public static final String TABLE_NAME = "schedule";
    public static final String ID = "_id";
    public static final String SCHEDULE_DATE = "schedule_date";
    public static final String SCHEDULE_TIME = "schedule_time";
    public static final String SCHEDULE_TITLE = "schedule_title";
    public static final String SCHEDULE_DETAIL = "schedule_detail";

    public static SQLiteDatabase getReadableDatabase(Context context) {
        ScheduleDbOpenHelper dbOpenHelper = new ScheduleDbOpenHelper(context);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        return db;
    }

    public static SQLiteDatabase getWritableDatabase(Context context) {
        ScheduleDbOpenHelper dbOpenHelper = new ScheduleDbOpenHelper(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        return db;
    }

    public static ScheduleDto createSchedule(Cursor cursor) {
        ScheduleDto schedule = new ScheduleDto();
        schedule.id = cursor.getLong(cursor.getColumnIndex(ID));
        schedule.date = cursor.getString(cursor.getColumnIndex(SCHEDULE_DATE));
        schedule.time = cursor.getString(cursor.getColumnIndex(SCHEDULE_TIME));
        schedule.title = cursor.getString(cursor.getColumnIndex(SCHEDULE_TITLE));
        schedule.detail = cursor.getString(cursor.getColumnIndex(SCHEDULE_DETAIL));
        return schedule;
    }

    public static List<ScheduleDto> createList(Cursor cursor) {
        List<ScheduleDto> list = null;
        if (cursor.moveToFirst()) {
            list = new ArrayList<ScheduleDto>();
            do {
                list.add(createSchedule(cursor));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public static ContentValues createContentValues(ScheduleDto schedule) {
        ContentValues contentValues = new ContentValues();
        if (schedule.id != null) {
            contentValues.put(ID, schedule.id);
        }
        contentValues.put(SCHEDULE_DATE, schedule.date);
        contentValues.put(SCHEDULE_TIME, schedule.time);
        contentValues.put(SCHEDULE_TITLE, schedule.title);
        contentValues.put(SCHEDULE_DETAIL, schedule.detail);
        return contentValues;
    }

    public static List<ScheduleDto> findByDate(Context context, String date) {
        SQLiteDatabase db = getReadableDatabase(context);
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + SCHEDULE_DATE + "=?" +
                " ORDER BY " + SCHEDULE_DATE + "," + SCHEDULE_TIME;
        Cursor cursor = db.rawQuery(sql, new String[]{date});
        List<ScheduleDto> list = createList(cursor);
        cursor.close();
        return list;
    }

    public static ScheduleDto findById(Context context, long id) {
        SQLiteDatabase db = getReadableDatabase(context);
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + ID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        ScheduleDto schedule = null;
        if (cursor.moveToFirst()) {
            schedule = createSchedule(cursor);
        }
        cursor.close();
        return schedule;
    }

    public static List<String> getDateList(Context context, String date) {
        SQLiteDatabase db = getReadableDatabase(context);
        String sql = "SELECT DISTINCT " + SCHEDULE_DATE + " FROM " + TABLE_NAME +
                " WHERE " + SCHEDULE_DATE + " LIKE ?" +
                " ORDER BY " + SCHEDULE_DATE;
        Cursor cursor = db.rawQuery(sql, new String[]{date});
        List<String> list = null;
        if (cursor.moveToFirst()) {
            list = new ArrayList<String>();
            do {
                list.add(cursor.getString(cursor.getColumnIndex(SCHEDULE_DATE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public static long insert(Context context, ScheduleDto schedule) {
        SQLiteDatabase db = getWritableDatabase(context);
        return db.insert(TABLE_NAME, null, createContentValues(schedule));
    }

    public static int update(Context context, ScheduleDto schedule) {
        SQLiteDatabase db = getWritableDatabase(context);
        return db.update(TABLE_NAME, createContentValues(schedule),
                ID + "=?", new String[]{schedule.id.toString()});
    }

    public static int delete(Context context, long id) {
        SQLiteDatabase db = getWritableDatabase(context);
        return db.delete(TABLE_NAME, ID + "=?",
                new String[]{String.valueOf(id)});
    }
}