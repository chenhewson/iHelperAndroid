package hewson.logindemo2.activity.MyAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;
import hewson.logindemo2.utils.AvatarUrl;
import hewson.logindemo2.vo.TaskVo;

public class Myadapter_star extends BaseAdapter {
    List<Map<String,Object>> list;
    Context context;

    //反射器
    LayoutInflater inflater;

    //构造器
    public Myadapter_star(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context=context;
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
        TextView item_money=(TextView)view.findViewById(R.id.item_money);
        TextView item_distinct=(TextView)view.findViewById(R.id.item_distinct);
        TextView item_distance=(TextView)view.findViewById(R.id.item_distance);

        //注入值,position是当前item的下标,这里map的key要和addorder_fragment里面的map对应
        Map<String, Object> map=list.get(position);
        TaskVo taskVo= (TaskVo) map.get("TaskVo");
        String avatar=(String)map.get("avatar");

        //图片显示框架
        loadGlide(avatar,item_userAvator);

        item_userName.setText(taskVo.gettDetail());
        item_orderTitle.setText(taskVo.gettTitle());
        item_money.setText(String.format("%.2f",taskVo.gettMoney()));
        item_distinct.setText(taskVo.gettAddress());
        item_distance.setText("");

//        map.put("distance",keyStr);
//        map.put("address",taskVo.gettAddress());
        return view;
    }

    //加载图片工具类
    private void loadGlide(String mUrl, ImageView mImageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.icon_avatar)
                .error(R.mipmap.icon_avatar);
        Glide.with(context).load(mUrl).apply(requestOptions).into(mImageView);
    }
}
