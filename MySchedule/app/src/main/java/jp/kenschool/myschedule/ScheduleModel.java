package jp.kenschool.myschedule;

/**
 * スケジュール
 */
public class ScheduleModel {
    public Long scheduleId;
    public String date;
    public String time;
    public String title;
    public String detail;

    @Override
    public String toString() {
        return "ScheduleModel{" +
                "scheduleId=" + scheduleId +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
