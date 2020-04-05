package hewson.logindemo2.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.MyAdapter.Myadapter_home;
import hewson.logindemo2.activity.home_activity;
import hewson.logindemo2.activity.login_activity;
import hewson.logindemo2.activity.register_activity;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.SharePreferencesUtil;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.TaskVo;
import hewson.logindemo2.vo.UserVo;

public class home_fragment extends Fragment {
    private List<Map<String,Object>> listmap=new ArrayList<Map<String,Object>>();
    Myadapter_home myadapter_home;
    ListView listView_home;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        listView_home=view.findViewById(R.id.listview_home);
        myadapter_home=new Myadapter_home(getActivity());
        getOrderList();
        System.out.println("onCreateView");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume()");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void getOrderList(){
        //1.拿到listview对象

        //获取保存在SharePreferences中当前登录的user
        SharePreferencesUtil util=SharePreferencesUtil.getSharePreferencesInstance(listView_home.getContext());
        String userinfo=util.readString("user");

        //解析json，转为user实体类
        Gson gson=new Gson();
        UserVo userVo=gson.fromJson(userinfo, UserVo.class);

        //获取当前user的id
        String userid=String.valueOf(userVo.getUserid());

        //发送请求
        OkhttpUtils.get(
                Const.IpAddress+"protal/Task/allTask.do?userid="+userid,
                new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);

                        //解析后台返回的列表信息
                        Gson gson=new Gson();
                        ServerResponse<List<TaskVo>> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<List<TaskVo>>>(){}.getType());

                        //2.获取数据源
                        List<TaskVo> list = serverResponse.getData();
                        List<Map<String,Object>> listmapTemp=new ArrayList<Map<String,Object>>();

                        if(list!=null){
                            listmap.clear();
                            for(TaskVo taskVo:list){
                                Map<String,Object> map=new HashMap<String,Object>();

                                map.put("item_userAvator",R.mipmap.icon_add_selected);
                                map.put("item_userName",taskVo.gettDetail());
                                map.put("item_orderTitle",taskVo.gettTitle());
                                listmapTemp.add(map);
                            }
                            listmap.addAll(listmapTemp);
                        }
                    }
                }
        );
        //3.设置适配器,用自定义adapter
        myadapter_home.setList(listmap);//给数据
        listView_home.setAdapter(myadapter_home);
    }
}
