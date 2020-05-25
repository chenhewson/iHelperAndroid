package hewson.logindemo2.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

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
import hewson.logindemo2.utils.AvatarUrl;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.SharePreferencesUtil;
import hewson.logindemo2.utils.myUserInfo;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.TaskVo;
import hewson.logindemo2.vo.UserVo;

public class home_fragment extends Fragment implements View.OnClickListener{
    private List<Map<String,Object>> listmap=new ArrayList<Map<String,Object>>();
    Myadapter_home myadapter_home;
    ListView listView_home;
    ImageView imageview_nothing;
    TextView textview_note;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if ( listView_home.getAdapter()==null) {
            if (myadapter_home==null) {
                myadapter_home = new Myadapter_home(getActivity());
                getOrderList();
            }else{
                getOrderList();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("view","view==null");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initMyView(view);
        Log.i("home_fragment","onCreateView");
        return view;
    }

    private void initMyView(View view) {
        listView_home=view.findViewById(R.id.listview_home);
        textview_note=view.findViewById(R.id.textview_note);
        imageview_nothing=view.findViewById(R.id.imageview_nothing);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("home_fragment","onResume");
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

                        if(treeMap!=null&&treeMap.size()!=0){
                            listmap.clear();
                            Set<Double> keys = treeMap.keySet();
                            for(Iterator iter = keys.iterator(); iter.hasNext();){
                                Map<String,Object> map=new HashMap<String,Object>();
                                Double keyStr = (Double) iter.next();//获取距离
                                TaskVo taskVo = treeMap.get(keyStr);//获取实体类

                                //自己发布的任务不展示在首页
                                if(!taskVo.getPublishuserid().equals(String.valueOf(myUserInfo.getuser(getActivity()).getUserid()))){
                                    //任务信息实体类
                                    map.put("taskVo",taskVo);

                                    //任务距离
                                    map.put("distance",showDistance(keyStr));

                                    //头像
                                    map.put("avatar",AvatarUrl.getAvatarUrl());
                                    listmapTemp.add(map);
                                }
                            }
                            if(listmapTemp==null||listmapTemp.size()==0){
                                //处理列表为空的情况
                                Message msge = new Message();

                                //设定任务列表为空时展示404图片和提示信息
                                msge.what = 2;
                                mHandler.sendMessage(msge);
                            }
                            listmap.addAll(listmapTemp);

                            Message msge = new Message();
                            msge.what = 1;//setadapter
                            msge.obj = listmap;
                            mHandler.sendMessage(msge);
                        }
                        else {
                            //处理列表为空的情况
                            Message msge = new Message();

                            //设定任务列表为空时展示404图片和提示信息
                            msge.what = 2;
                            mHandler.sendMessage(msge);
                        }
                    }
                }
        );

        listView_home.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        Map<String,Object> map= (Map<String, Object>) myadapter_home.getItem(position);

                        //获取任务实体类
                        TaskVo taskVo= (TaskVo) map.get("taskVo");

                        //获取距离
                        String distance=(String)map.get("distance");

                        //获取头像
                        String avatar=(String)map.get("avatar");

                        //把参数放到bundle，实现activity传参
                        bundle.putString("item_userAvator",avatar);
                        bundle.putString("item_userName",taskVo.gettDetail());
                        bundle.putString("item_orderTitle",taskVo.gettTitle());
                        bundle.putString("distance",distance);
                        bundle.putString("address",taskVo.gettAddress());
                        bundle.putString("money",String.valueOf(taskVo.gettMoney()));
                        bundle.putString("taskid",String.valueOf(taskVo.getTaskid()));

                        bundle.putDouble("jingdu",taskVo.gettJingdu().doubleValue());
                        bundle.putDouble("weidu",taskVo.gettWeidu().doubleValue());

                        Intent intent=new Intent(getContext(),OrderDetail.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
        );
    }

    //加载图片工具类
    private void loadGlide(String mUrl,ImageView mImageView) {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.mipmap.icon_nothing);
        Glide.with(this)
                .load(mUrl)
                .apply(requestOptions)
                .into(mImageView);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    List<Map<String,Object>> list = (List<Map<String,Object>>)msg.obj;

                    myadapter_home.setList(list);//给数据12188，12144，12252
                    myadapter_home.notifyDataSetChanged();
                    listView_home.setAdapter(myadapter_home);
                    break;
                case 2:{
                    //设定任务列表为空时展示404图片和提示信息
                    textview_note.setText("任务列表为空！");
                    imageview_nothing.setImageResource(R.mipmap.icon_nothing);
                }break;
            }
        }
    };

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.linearlayout_heart:
//                imageview_heart.setImageResource(R.mipmap.imageview_heart_selected);
//                break;
        }
    }
}
