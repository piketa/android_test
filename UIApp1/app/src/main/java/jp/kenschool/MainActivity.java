package jp.kenschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        LinearLayout container = new LinearLayout(this);
        container.setLayoutParams(layoutParams1);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(10,10,10,10);

        TextView title = new TextView(this);
        title.setLayoutParams(layoutParams2);
        title.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        title.setText("会員登録ページ");
        title.setPadding(0,0,0,60);

        LinearLayout horizontalLayout = new LinearLayout(this);
        horizontalLayout.setLayoutParams(layoutParams2);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setPadding(0,0,0,60);

        TextView emailTextView = new TextView(this);
        emailTextView.setLayoutParams(layoutParams3);
        emailTextView.setText("メールアドレス");

        EditText emailEditText = new EditText(this);
        emailEditText.setLayoutParams(layoutParams2);

        Button submit = new Button(this);
        submit.setLayoutParams(layoutParams3);
        submit.setText("送　信");

        horizontalLayout.addView(emailTextView);
        horizontalLayout.addView(emailEditText);
        container.addView(title);
        container.addView(horizontalLayout);
        container.addView(submit);

        setContentView(container);
    }
}
