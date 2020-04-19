package hewson.logindemo2.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.MyAdapter.Myadapter_home;
import hewson.logindemo2.activity.OrderDetail;
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
        getOrderList();
        Log.i("home_fragment","onCreateView");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("home_fragment","onResume");
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
        myadapter_home=new Myadapter_home(getContext());
        //1.拿到listview对象

        //获取保存在SharePreferences中当前登录的user
        final SharePreferencesUtil util=SharePreferencesUtil.getSharePreferencesInstance(listView_home.getContext());
        String userinfo=util.readString("user");

        //解析json，转为user实体类
        Gson gson=new Gson();
        final UserVo userVo=gson.fromJson(userinfo, UserVo.class);

        //获取当前user的id
        final String userid=String.valueOf(userVo.getUserid());

        //发送请求
        OkhttpUtils.get(
                Const.IpAddress+"protal/Task/homeTask.do?jingdu="+Const.LONGITUDE+"&weidu="+Const.LATITUDE,
                new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);

                        //解析后台返回的列表信息
                        Gson gson=new Gson();
                        ServerResponse<TreeMap<Double,TaskVo>> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<TreeMap<Double,TaskVo>>>(){}.getType());

//                        //2.获取数据源
                        List<Map<String,Object>> listmapTemp=new ArrayList<Map<String,Object>>();

                        //2、获取数据源
                        TreeMap<Double,TaskVo> treeMap=serverResponse.getData();
                        Set<Double> keys = treeMap.keySet();
                        List<TaskVo> list =new LinkedList<TaskVo>();
                        if(treeMap.size()!=0){
//                            listmap.clear();
                            for(Iterator iter = keys.iterator(); iter.hasNext();){
                                Map<String,Object> map=new HashMap<String,Object>();
                                Double keyStr = (Double) iter.next();//获取距离
                                TaskVo taskVo = treeMap.get(keyStr);//获取实体类

                                //自己发布的任务不展示在首页
                                if(!taskVo.getPublishuserid().equals(userid)){
                                    map.put("item_userAvator",R.mipmap.icon_avatar);
                                    map.put("item_userName",taskVo.gettDetail());//这里是任务详情
                                    map.put("item_orderTitle",taskVo.gettTitle());
                                    map.put("distance",showDistance(keyStr));
                                    map.put("address",taskVo.gettAddress());
                                    map.put("money",taskVo.gettMoney());

                                    map.put("taskid",String.valueOf(taskVo.getTaskid()));
                                    listmapTemp.add(map);
                                }

                            }
                            listmap.addAll(listmapTemp);
                        }
                    }
                }
        );
        //3.设置适配器,用自定义adapter
        myadapter_home.setList(listmap);//给数据
        myadapter_home.notifyDataSetChanged();
        listView_home.setAdapter(myadapter_home);

        listView_home.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        Map<String,Object> map= (Map<String, Object>) myadapter_home.getItem(position);

                        //把参数放到bundle，实现activity传参
                        bundle.putInt("item_userAvator",R.mipmap.icon_avatar);
                        bundle.putString("item_userName",(String)map.get("item_userName"));
                        bundle.putString("item_orderTitle",(String)map.get("item_orderTitle"));
                        bundle.putString("distance",(String)map.get("distance"));
                        bundle.putString("address",(String) map.get("address"));
                        bundle.putString("money",String.valueOf(map.get("money")));
                        bundle.putString("taskid",String.valueOf(map.get("taskid")));

                        Intent intent=new Intent(getContext(),OrderDetail.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
        );
    }

    public String showDistance(Double doublemoney){
        //米，百米，1公里
        if(doublemoney>=0&&doublemoney!=null){
            if(doublemoney==0&&doublemoney<50){
                return "50米";
            }
            if(doublemoney>=50&&doublemoney<100){
                return "100米";
            }
            if((doublemoney>=100)&&(doublemoney<1000)){
                int i=new Double(doublemoney).intValue();
                return String.valueOf(i)+"米";
            }
            if((doublemoney/1000)<=10&&(doublemoney>=1000)){
                return String.format("%.2f",(doublemoney/1000))+"公里";
            }
            return ">10公里";
        }else {
            return "暂无";
        }
    }
}
