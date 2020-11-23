package jp.kenschool.toolbarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private TextView textViewAction;
    private ImageView imageViewCamera;
    private static final int REQUEST_CODE_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 各ビューの取得
        textViewAction = (TextView)findViewById(R.id.textViewAction);
        Toolbar toolbarCustom = (Toolbar)findViewById(R.id.toolbarCustom);
        Button buttonCustom = (Button)findViewById(R.id.buttonCustom);
        imageViewCamera = (ImageView)findViewById(R.id.imageViewCamera);
        // ツールバーの設定
        toolbarCustom.setNavigationIcon(R.drawable.baseline_arrow_back_black_24);
        toolbarCustom.setLogo(android.R.drawable.sym_def_app_icon);
        toolbarCustom.setTitle("Title");
        toolbarCustom.setSubtitle("Sub Title");
        toolbarCustom.setBackgroundColor(Color.parseColor("#CCE6FF"));
        // ツールバー内のボタンのクリックイベント処理
        buttonCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAction.setText("Custom Button Action");
                Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jp.kenschool/"));
                startActivity(intentWeb);
            }
        });
        // onCreateOptionsMenu()メソッドを呼び出しオプションメニューの設定を行う
        setSupportActionBar(toolbarCustom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // メニュー内のどのアイテムがクリックされたか判定
        switch (item.getItemId()) {
            case android.R.id.home:
                textViewAction.setText("Navigation Icon Action");
                return true;
            case R.id.actionCall:
                textViewAction.setText("Call Action");
                Intent intentDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0368535840"));
                startActivity(intentDial);
                return true;
            case R.id.actionCamera:
                textViewAction.setText("Camera Action");
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
                return true;
            case R.id.actionSearch:
                textViewAction.setText("Search Action");
                SearchView searchView = (SearchView)item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    // 検索処理
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        textViewAction.setText("Submit Action");
                        Intent intentSearch = new Intent(Intent.ACTION_WEB_SEARCH);
                        intentSearch.putExtra(SearchManager.QUERY, query);
                        startActivity(intentSearch);
                        return false;
                    }
                    // 検索キーワードが変更された時の処理
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_CODE_CAMERA && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap bitmap = (Bitmap)extras.get("data");
                imageViewCamera.setImageBitmap(bitmap);
            }
        }
    }
}
