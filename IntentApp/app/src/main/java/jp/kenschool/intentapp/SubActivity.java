package jp.kenschool.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String attendance = intent.getStringExtra("ATTENDANCE");
        String message = name + "さん、" + attendance + "で登録しました";
        TextView textViewMessage = (TextView)findViewById(R.id.textViewMessage);
        textViewMessage.setText(message);
    }
}
