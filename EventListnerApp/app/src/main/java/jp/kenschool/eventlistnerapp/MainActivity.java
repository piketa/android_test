package jp.kenschool.eventlistnerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerBackground;
    private RadioGroup radioGroupSize;
    private CheckBox checkBoxVisible;
    private TextView textViewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // レイアウトXMLファイル内の各ビューの参照を取得
        spinnerBackground = (Spinner)findViewById(R.id.spinnerBackground);
        radioGroupSize = (RadioGroup)findViewById(R.id.radioGroupSize);
        checkBoxVisible = (CheckBox)findViewById(R.id.checkboxVisible);
        textViewContent = (TextView)findViewById(R.id.textViewContent);
        // Spinnerにアダプターを設定
        String[] colors = {"white", "red", "blue", "green", "yellow", "cyan", "magenta", "gray"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, colors);
        spinnerBackground.setAdapter(arrayAdapter);
        // Spinnerのアイテム選択時のイベント処理を登録
        spinnerBackground.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String color = (String)adapterView.getSelectedItem();
                textViewContent.setBackgroundColor(Color.parseColor(color));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        // RadioGroup内のRadioButtonのチェックが変更された時のイベント処理の登録
        radioGroupSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton)findViewById(i);
                String size = radioButton.getText().toString();
                int textSize = Integer.parseInt(size);
                textViewContent.setTextSize(textSize);
            }
        });
        // CheckBoxのチェックが変更された時のイベント処理の登録
        checkBoxVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                textViewContent.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
            }
        });
        // TextViewクリック時のイベント処理の登録
        textViewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewContent.setText("Clicked!!");
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textViewContentText", textViewContent.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String textViewContentText = savedInstanceState.getString("textViewContentText");
        textViewContent.setText(textViewContentText);
    }
}
