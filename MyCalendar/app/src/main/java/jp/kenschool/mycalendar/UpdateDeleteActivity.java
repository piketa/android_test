package jp.kenschool.mycalendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.util.Calendar;

import jp.kenschool.mycalendar.dao.ScheduleDao;
import jp.kenschool.mycalendar.dto.ScheduleDto;
import jp.kenschool.mycalendar.view.MyCalendarView;

public class UpdateDeleteActivity extends AppCompatActivity {
    public static String TAG = UpdateDeleteActivity.class.getName();
    private ScheduleDto mSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        //インテントに選択されたIDを取得
        long id = getIntent().getLongExtra(ListActivity.INTENT_SCHEDULE_ID, 0);
        //データベース検索
        mSchedule = ScheduleDao.findById(getApplicationContext(), id);
        //colors.xmlから背景色を取得
        int colorAliceBlue = ContextCompat.getColor(this, R.color.color_alice_blue);

        //ビューとレイアウトの取得
        final TextView dateTextView =
                (TextView) findViewById(R.id.updateDeleteActivity_date_textView);
        final TextView timeTextView =
                (TextView) findViewById(R.id.updateDeleteActivity_time_textView);
        final EditText detailEditText =
                (EditText) findViewById(R.id.updateDeleteActivity_detail_editText);
        final EditText titleEditText =
                (EditText) findViewById(R.id.updateDeleteActivity_title_editText);
        TextView navigationTextView =
                (TextView) findViewById(R.id.updateDeleteActivity_navigation_textView);
        Button deleteButton =
                (Button) findViewById(R.id.updateDeleteActivity_deleteButton);
        Button updateButton =
                (Button) findViewById(R.id.updateDeleteActivity_updateButton);

        //ビューとレイアウトの設定
        navigationTextView.setText(mSchedule.date);
        navigationTextView.setText("＜ " + mSchedule.date);
        navigationTextView.setBackgroundColor(colorAliceBlue);
        navigationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //インテントに選択された日付をセット
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(MainActivity.INTENT_SELECTED_DATE, mSchedule.date);
                //アクティビティをスタート
                startActivity(intent);
            }
        });

        dateTextView.setText(mSchedule.date);
        dateTextView.setBackgroundColor(Color.WHITE);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                try {
                    calendar.setTime(MyCalendarView.DEFAULT_DATE_FORMAT.parse(mSchedule.date));
                } catch (ParseException e) {
                    Log.e(TAG, e.getMessage());
                }
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        mSchedule.date = MyCalendarView.DEFAULT_DATE_FORMAT.format(calendar.getTime());
                        dateTextView.setText(mSchedule.date);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        v.getContext(),
                        android.R.style.Theme_DeviceDefault_Light,
                        onDateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        timeTextView.setText(mSchedule.time);
        timeTextView.setBackgroundColor(Color.WHITE);
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                try {
                    calendar.setTime(MyCalendarView.TIME_FORMAT.parse(mSchedule.time));
                } catch (ParseException e) {
                    Log.e(TAG, e.getMessage());
                }
                Log.d(TAG, "h " + calendar.get(Calendar.HOUR_OF_DAY));
                Log.d(TAG, "m " + calendar.get(Calendar.MINUTE));

                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        mSchedule.time = MyCalendarView.TIME_FORMAT.format(calendar.getTime());
                        timeTextView.setText(mSchedule.time);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), onTimeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();

            }
        });

        titleEditText.setText(mSchedule.title);
        titleEditText.setBackgroundColor(Color.WHITE);
        detailEditText.setText(mSchedule.detail);
        detailEditText.setBackgroundColor(Color.WHITE);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //データベースを更新
                mSchedule.date = dateTextView.getText().toString();
                mSchedule.time = timeTextView.getText().toString();
                mSchedule.title = titleEditText.getText().toString();
                mSchedule.detail = detailEditText.getText().toString();
                ScheduleDao.update(getApplicationContext(), mSchedule);
                //インテントに選択された日付をセット
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(MainActivity.INTENT_SELECTED_DATE, mSchedule.date);
                //アクティビティをスタート
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("削除しますか");
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //データベースから削除
                        ScheduleDao.delete(getApplicationContext(), mSchedule.id);
                        //インテントに選択された日付をセット
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(MainActivity.INTENT_SELECTED_DATE, mSchedule.date);
                        //アクティビティをスタート
                        startActivity(intent);
                    }
                });
                builder.create().show();
            }
        });
    }
}