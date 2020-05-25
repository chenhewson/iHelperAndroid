package hewson.logindemo2.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import hewson.logindemo2.activity.MyAdapter.Myadapter_star;
import hewson.logindemo2.activity.OrderDetail;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.AvatarUrl;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.myUserInfo;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.TaskVo;
import hewson.logindemo2.vo.UserVo;

public class star_fragment extends Fragment {
    private List<Map<String,Object>> listmap=new ArrayList<Map<String,Object>>();
    Myadapter_star myadapter_star;
    ListView listView_star;
    ImageView imageview_nothing;
    TextView textview_note;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            Log.i("view","view==null");
            view = inflater.inflate(R.layout.fragment_star, container, false);
            initMyView(view);
            myadapter_star=new Myadapter_star(getContext());
            getOrderList();
        }
        Log.i("home_fragment","onCreateView");
        return view;
    }

    private void initMyView(View view) {
        listView_star=view.findViewById(R.id.listview_star);
        imageview_nothing=view.findViewById(R.id.imageview_nothing);
        textview_note=view.findViewById(R.id.textview_note);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getOrderList(){
        //获取保存在SharePreferences中当前登录的user
        UserVo userVo= myUserInfo.getuser(getContext());

        //获取当前user的id
        String userid=String.valueOf(userVo.getUserid());

        //发送请求
        OkhttpUtils.get(
                Const.IpAddress+"protal/Task/MyStar.do?userid="+userid,
                new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        //1.获得msg数据源
                        super.OnFinish(status, msg);

                        //2.解析后台返回的列表信息
                        Gson gson=new Gson();
                        ServerResponse<List<TaskVo>> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<List<TaskVo>>>(){}.getType());
                        //3.得到tasklist
                        List<TaskVo> taskVoList=serverResponse.getData();
                        if(taskVoList!=null&&taskVoList.size()!=0){
                            //4.tasklist不为空，将tasklist中的每个元素绑定到listmap中
                            listmap.clear();
                            List<Map<String,Object>> listmapTemp=new ArrayList<Map<String,Object>>();
                            for (TaskVo item:taskVoList){
                                Map<String,Object> map=new HashMap<String,Object>();
                                map.put("TaskVo",item);
                                //头像
                                map.put("avatar", AvatarUrl.getAvatarUrl());
                                listmapTemp.add(map);
                            }
                            listmap.addAll(listmapTemp);

                            Message msge = new Message();
                            msge.what = 1;//setadapter
                            msge.obj = listmap;
                            mHandler.sendMessage(msge);
                        }else {
                            Message msge = new Message();
                            msge.what = 2;//setadapter
                            mHandler.sendMessage(msge);
                        }


                    }
                }
        );
        listView_star.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        Map<String,Object> map= (Map<String, Object>) myadapter_star.getItem(position);
                        TaskVo taskVo= (TaskVo) map.get("TaskVo");
                        String avatar=(String)map.get("avatar");



                        //把参数放到bundle，实现activity传参
                        bundle.putString("item_userAvator",avatar);
                        bundle.putString("item_userName",taskVo.gettDetail());
                        bundle.putString("item_orderTitle",taskVo.gettTitle());
                        bundle.putString("distance","");
                        bundle.putString("address",taskVo.gettAddress());
                        bundle.putString("money",String.valueOf(taskVo.gettMoney()));
                        bundle.putString("taskid",String.valueOf(taskVo.getTaskid()));

                        bundle.putDouble("jingdu",taskVo.gettJingdu().doubleValue());
                        bundle.putDouble("weidu",taskVo.gettWeidu().doubleValue());

                        Intent intent=new Intent(getContext(), OrderDetail.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
        );
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    List<Map<String,Object>> list = (List<Map<String,Object>>)msg.obj;


                    myadapter_star.setList(list);//给数据
                    myadapter_star.notifyDataSetChanged();
                    listView_star.setAdapter(myadapter_star);
                }break;
                case 2:{
                    textview_note.setText("心愿单为空！快去关注一波~");
                    imageview_nothing.setImageResource(R.mipmap.icon_nothing);
                }break;

            }
        }
    };
}
