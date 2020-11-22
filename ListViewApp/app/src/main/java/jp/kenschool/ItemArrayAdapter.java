package jp.kenschool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<ItemBean> {
    public ItemArrayAdapter(@NonNull Context context, int resource, @NonNull List<ItemBean> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // データをはじめて表示する場合、各行のレイアウトを指定
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, null);
        }
        // 各行のデータを取得
        ItemBean bean = getItem(position);
        // 各行の TextView を取得
        TextView textViewItem = (TextView)convertView.findViewById(R.id.textViewItem);
        TextView textViewPrice = (TextView)convertView.findViewById(R.id.textViewPrice);
        // 各行の TextView にデータを出力
        textViewItem.setText(bean.item);
        textViewPrice.setText("￥" + Integer.toString(bean.price));
        return convertView;
    }
}
