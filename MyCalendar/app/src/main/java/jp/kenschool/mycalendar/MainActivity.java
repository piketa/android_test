package jp.kenschool.mycalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.text.ParseException;
import java.util.Date;

import jp.kenschool.mycalendar.view.MyCalendarView;

public class MainActivity extends AppCompatActivity {
    public static final String INTENT_SELECTED_DATE = "selectedDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //インテントから選択された日付を取得
        String selectedDate = getIntent().getStringExtra(INTENT_SELECTED_DATE);

        //MainActivityへ戻ってきたときの処理
        if (selectedDate != null) {
            MyCalendarView myCalendarView = (MyCalendarView) findViewById(R.id.mainActivity_myCalendarView);
            Date date = null;
            try {
                date = MyCalendarView.DEFAULT_DATE_FORMAT.parse(selectedDate);
            } catch (ParseException e) {
                Log.e(MainActivity.class.getName(), "Check the selectedDate.", e);
                date = new Date();
            }
            myCalendarView.updateCalendar(date);
        }
    }
}
