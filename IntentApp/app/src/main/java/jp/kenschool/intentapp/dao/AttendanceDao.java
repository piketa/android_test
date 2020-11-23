package jp.kenschool.intentapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import jp.kenschool.intentapp.dto.AttendanceDto;

public class AttendanceDao {
    public static String TABLE_NAME = "attendance";
    public static String COLUMN_ID = "_id";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_ATTENDANCE = "attendance";

    // 登録処理
    public static long insert(Context context, AttendanceDto attendanceDto) {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        long rowId = 0;
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, attendanceDto.name);
        values.put(COLUMN_ATTENDANCE, attendanceDto.attendance);
        rowId = db.insert(TABLE_NAME, null, values);
        return rowId;
    }

    // 全件検索
    public static List<AttendanceDto> findAllAttendance(Context context) {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM attendance";
        Cursor cursor = db.rawQuery(sql, null);

        List<AttendanceDto> attendanceList = new ArrayList<>();
        while (cursor.moveToNext()) {
            AttendanceDto attendanceDto = new AttendanceDto();
            attendanceDto.id = cursor.getLong(0);
            attendanceDto.name = cursor.getString(1);
            attendanceDto.attendance = cursor.getString(2);
            attendanceList.add(attendanceDto);
        }
        cursor.close();
        return attendanceList;
    }
}
