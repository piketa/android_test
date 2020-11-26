package jp.kenschool.mycalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import jp.kenschool.mycalendar.dao.ScheduleDao;
import jp.kenschool.mycalendar.dto.ScheduleDto;
import jp.kenschool.mycalendar.view.MyCalendarView;

public class ListActivity extends AppCompatActivity {
    public static final String INTENT_SCHEDULE_ID = "scheduleId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //インテントに選択された日付を取得
        String selectedDate = getIntent().getStringExtra(MainActivity.INTENT_SELECTED_DATE);
        if (selectedDate == null) {
            selectedDate = MyCalendarView.DEFAULT_DATE_FORMAT.format(new Date());
        }

        //ビューとレイアウトの取得
        TextView navigationTextView =
                (TextView) findViewById(R.id.listActivity_navigation_textView);
        final TextView dateTextView =
                (TextView) findViewById(R.id.listActivity_date_textView);
        Button registrationButton =
                (Button) findViewById(R.id.listActivity_registration_button);
        ListView listView =
                (ListView) findViewById(R.id.listActivity_listView);
        //colors.xmlから背景色を取得
        int colorAliceBlue = ContextCompat.getColor(this, R.color.color_alice_blue);

        //ナビゲーションテキストの設定
        navigationTextView.setText("＜  " + selectedDate.substring(0, selectedDate.lastIndexOf("/")));
        navigationTextView.setBackgroundColor(colorAliceBlue);
        navigationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //インテントに選択された日付をセット
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(MainActivity.INTENT_SELECTED_DATE,
                        dateTextView.getText().toString());
                //アクティビティをスタート
                startActivity(intent);
            }
        });

        //日付テキストビューの設定
        dateTextView.setText(selectedDate);

        //登録ボタンの設定
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //インテントに選択された日付をセット
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(MainActivity.INTENT_SELECTED_DATE,
                        dateTextView.getText().toString());
                //アクティビティをスタート
                startActivity(intent);
            }
        });

        //データベース検索
        List<ScheduleDto> scheduleList = ScheduleDao.findByDate(getApplicationContext(), selectedDate);

        ArrayAdapter<ScheduleDto> arrayAdapter =
                new ArrayAdapter<ScheduleDto>(this, android.R.layout.simple_list_item_1);
        //スジュールが登録されていない場合
        if (scheduleList == null) {
            ScheduleDto schedule = new ScheduleDto();
            schedule.id = -1L;
            arrayAdapter.add(schedule);
            listView.setAdapter(arrayAdapter);
        }
        //スジュールが登録されている場合
        else {
            for (ScheduleDto schedule : scheduleList) {
                arrayAdapter.add(schedule);
            }
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //ScheduleDtoを取得
                    ScheduleDto schedule = (ScheduleDto) parent.getItemAtPosition(position);
                    //インテントに選択された日付をセット
                    Intent intent = new Intent(getApplicationContext(), UpdateDeleteActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //スケジュールID
                    intent.putExtra(INTENT_SCHEDULE_ID, schedule.id);
                    //アクティビティをスタート
                    startActivity(intent);
                }
            });
        }
    }
}