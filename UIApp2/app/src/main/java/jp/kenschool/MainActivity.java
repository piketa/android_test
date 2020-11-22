package jp.kenschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button buttonRegistration;
    private Button buttonCancel;
    private EditText editTextTitle;
    private EditText editTextMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegistration = (Button)findViewById(R.id.buttonRegistration);
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        editTextTitle = (EditText)findViewById(R.id.editTitle);
        editTextMemo = (EditText)findViewById(R.id.editTextMemo);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder message = new StringBuilder();
                message.append("クリックイベント");
                message.append('\n');
                message.append("[タイトル]");
                message.append('\n');
                message.append(editTextTitle.getText().toString());
                message.append('\n');
                message.append("[メモ]");
                message.append('\n');
                message.append(editTextMemo.getText().toString());
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editTextTitle.setText(null);
                editTextMemo.setText(null);
                Toast.makeText(MainActivity.this, "ロングクリックイベント", Toast.LENGTH_SHORT).show();
                // 戻り値がtrueの場合は、onClickイベントが発生しない
                return true;
            }
        });
    }
}
