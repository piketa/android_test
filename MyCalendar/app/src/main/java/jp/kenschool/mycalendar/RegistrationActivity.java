package jp.kenschool.mycalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Date;

import jp.kenschool.mycalendar.dao.ScheduleDao;
import jp.kenschool.mycalendar.dto.ScheduleDto;
import jp.kenschool.mycalendar.view.MyCalendarView;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //インテントに選択された日付を取得
        String selectedDate = getIntent().getStringExtra(MainActivity.INTENT_SELECTED_DATE);
        if (selectedDate == null) {
            selectedDate = MyCalendarView.DEFAULT_DATE_FORMAT.format(new Date());
        }

        //ビューとレイアウトの取得
        TextView navigationTextView =
                (TextView) findViewById(R.id.registrationActivity_navigation_textView);
        final TextView dateTextView =
                (TextView) findViewById(R.id.registrationActivity_date_textView);
        final TimePicker timePicker =
                (TimePicker) findViewById(R.id.registrationActivity_timePicker);
        Button registrationButton =
                (Button) findViewById(R.id.registrationActivity_button);
        //colors.xmlから背景色を取得
        int colorAliceBlue = ContextCompat.getColor(this, R.color.color_alice_blue);

        //ビューとレイアウトの設定
        navigationTextView.setText("＜  " + selectedDate);
        navigationTextView.setBackgroundColor(colorAliceBlue);
        navigationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //インテントに選択された日付をセット
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(MainActivity.INTENT_SELECTED_DATE,
                        dateTextView.getText().toString());
                //アクティビティをスタート
                startActivity(intent);
            }
        });

        dateTextView.setText(selectedDate);
        timePicker.setIs24HourView(true);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText =
                        (EditText) findViewById(R.id.registrationActivity_title_editText);
                EditText detailEditText =
                        (EditText) findViewById(R.id.registrationActivity_detail_editText);
                String title = titleEditText.getText().toString();
                String detail = detailEditText.getText().toString();

                //titleの入力チェック
                if (title.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Title is required", Toast.LENGTH_LONG).show();
                    return;
                }
                //detailの入力チェック
                if (detail.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Detail is required", Toast.LENGTH_LONG).show();
                    return;
                }

                //タイムピッカーから時刻を取得
                Calendar time = Calendar.getInstance();
                time.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                time.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                //スケジュールモデルを生成し、入力値を設定
                ScheduleDto schedule = new ScheduleDto();
                schedule.date = dateTextView.getText().toString();
                schedule.time = MyCalendarView.TIME_FORMAT.format(time.getTime());
                schedule.title = title;
                schedule.detail = detail;

                //データベース検索
                ScheduleDao.insert(getApplicationContext(), schedule);

                //インテントに選択された日付をセット
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(MainActivity.INTENT_SELECTED_DATE,
                        dateTextView.getText().toString());

                //アクティビティをスタート
                startActivity(intent);
            }
        });
    }
}