package jp.kenschool.mycalendar.dto;

/**
 * スケジュール
 */

public class ScheduleDto {
    public Long id;
    public String date;
    public String time;
    public String title;
    public String detail;

    @Override
    public String toString() {
        if (id == -1) {
            return "スケジュールは登録されていません";
        }
        return time + "  " + title;
    }
}
