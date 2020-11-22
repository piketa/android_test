package jp.kenschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ListView を取得
        ListView listViewItem = (ListView)findViewById(R.id.listViewItem);
        // アダプターに渡すデータを作成
        String[] items = {"えんぴつ", "消しゴム", "ボールペン", "定規"};
        int[] prices = {80, 100, 120, 150};
        List<ItemBean> beans = new ArrayList<>();
        for(int i = 0; i < items.length; i++) {
            ItemBean bean = new ItemBean();
            bean.item = items[i];
            bean.price = prices[i];
            beans.add(bean);
        }
        // アダプターオブジェクトの生成
        ItemArrayAdapter adapter = new ItemArrayAdapter(this, 0, beans);
        // ListView にアダプターをセット
        listViewItem.setAdapter(adapter);
    }
}
