package hewson.logindemo2.activity.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;
/*
* 用于操作listview_home，填充数据*/
public class Myadapter_home extends BaseAdapter {
    List<Map<String,Object>> list;

    //反射器
    LayoutInflater inflater;

    //构造器
    public Myadapter_home(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    //自动生成
    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    //改
    @Override
    public int getCount() {
        return list.size();
    }

    //改
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    //改
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //反射出item视图对象
        View view=inflater.inflate(R.layout.listview_item,null);

        //获取item布局中的组件的id
        ImageView item_userAvator=(ImageView)view.findViewById(R.id.item_userAvator);
        TextView item_userName=(TextView)view.findViewById(R.id.item_userName);
        TextView item_orderTitle=(TextView)view.findViewById(R.id.item_orderTitle);

        //注入值,position是当前item的下标,这里map的key要和addorder_fragment里面的map对应
        Map map=list.get(position);
        item_userAvator.setImageResource((Integer) map.get("item_userAvator"));//后台需要重新定义一个实体类来保存复合信息
        item_userName.setText((String)map.get("item_userName"));
        item_orderTitle.setText((String)map.get("item_orderTitle"));

        return view;
    }
}