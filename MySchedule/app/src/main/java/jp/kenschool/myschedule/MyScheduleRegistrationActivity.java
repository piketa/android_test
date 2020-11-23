package jp.kenschool.myschedule;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;

public class MyScheduleRegistrationActivity extends AppCompatActivity {

    private TextView txvDate;
    private EditText edtHour;
    private EditText edtMinute;
    private EditText edtTitle;
    private EditText edtDetail;
    private Button btnRegistration;
    private Button btnCancel;
    private ScheduleDao dao;

    private void showAlertDialogOkOnly(String title, String message) {
        //アラートダイアログの設定
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null);
        //アラートダイアログを表示する
        builder.create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule_registration);

        //各Viewを取得
        txvDate = (TextView) findViewById(R.id.txvDate);
        edtHour = (EditText) findViewById(R.id.edtHour);
        edtMinute = (EditText) findViewById(R.id.edtMinute);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtDetail = (EditText) findViewById(R.id.edtDetail);
        btnRegistration = (Button) findViewById(R.id.btnRegistration);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        //日付表示用TextViewの初期設定
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd(E)");
        txvDate.setText(dateFormat.format(date));

        //登録ボタンのクリックイベントを設定
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //時刻の未入力チェック
                String hour = edtHour.getText().toString();
                String minute = edtMinute.getText().toString();
                if (hour.equals("") || minute.equals("")) {
                    showAlertDialogOkOnly("入力チェック", "時刻が未入力です");
                    return;
                } else {
                    //時刻の不正入力チェック
                    try {
                        int h = Integer.parseInt(hour);
                        if (h < 0 || h > 23) {
                            throw new NumberFormatException();
                        }
                        int m = Integer.parseInt(minute);
                        if (m < 0 || m > 59) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        showAlertDialogOkOnly("入力チェック", "時刻が不正です");
                        return;
                    }
                }
                //タイトルの未入力チェック
                String title = edtTitle.getText().toString();
                if (title.equals("")) {
                    showAlertDialogOkOnly("入力チェック", "タイトルが未入力です");
                    return;
                }
                //詳細の未入力チェック
                String detail = edtDetail.getText().toString();
                if (detail.equals("")) {
                    showAlertDialogOkOnly("入力チェック", "詳細が未入力です");
                    return;
                }


                //登録処理
                ScheduleModel schedule = new ScheduleModel();
                schedule.date = txvDate.getText().toString();
                schedule.time = hour + ":" + minute;
                schedule.title = title;
                schedule.detail = detail;

                dao = new ScheduleDao();
                dao.insert(v.getContext(), schedule);

                //登録確認
                StringBuilder message = new StringBuilder();
                message.append(txvDate.getText())
                        .append(hour).append('時')
                        .append(minute).append('分').append('\n')
                        .append("タイトル:").append(title).append('\n')
                        .append("詳細:").append(detail).append("\n")
                        .append("登録が完了しました。");
                Toast.makeText(v.getContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        //取消ボタンのクリックイベントを設定
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //この課題では使用しません
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_schedule_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
