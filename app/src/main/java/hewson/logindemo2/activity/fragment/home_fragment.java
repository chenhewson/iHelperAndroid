package hewson.logindemo2.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.MyAdapter.Myadapter_home;

public class home_fragment extends Fragment {
    ListView listView_home;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        listView_home=view.findViewById(R.id.listview_home);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //1.拿到listview对象

        //2.数据源,要改
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=new HashMap<String,Object>();

        map.put("item_userAvator",R.mipmap.icon_add_selected);
        map.put("item_userName","陈晓森");
        map.put("item_orderTitle","打饭");
        list.add(map);

        map.put("item_userAvator",R.mipmap.icon_add_selected);
        map.put("item_userName","周剑鸣");
        map.put("item_orderTitle","取快递");
        list.add(map);

        //3.设置适配器,用自定义adapter
        Myadapter_home myadapter_home=new Myadapter_home(listView_home.getContext());
        myadapter_home.setList(list);//给数据

        listView_home.setAdapter(myadapter_home);
    }
}
