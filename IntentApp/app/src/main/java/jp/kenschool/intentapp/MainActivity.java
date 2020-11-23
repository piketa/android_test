package jp.kenschool.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import jp.kenschool.intentapp.dao.AttendanceDao;
import jp.kenschool.intentapp.dto.AttendanceDto;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName;
    private Spinner spinnerAttendance;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText)findViewById(R.id.editTextName);
        spinnerAttendance = (Spinner)findViewById(R.id.spinnerAttendance);
        buttonSend = (Button)findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "氏名が未入力です", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {  // 登録処理
                    // Dtoオブジェクトの設定
                    AttendanceDto attendanceDto = new AttendanceDto();
                    attendanceDto.name = editTextName.getText().toString();
                    attendanceDto.attendance = spinnerAttendance.getSelectedItem().toString();
                    // DB登録
                    long rowId = AttendanceDao.insert(getApplicationContext(), attendanceDto);
                    if (rowId > 0) {
                        Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                        intent.putExtra("NAME", name);
                        intent.putExtra("ATTENDANCE", spinnerAttendance.getSelectedItem().toString());
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
