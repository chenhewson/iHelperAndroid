package hewson.logindemo2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.MyAdapter.Myadapter_orderclassfier;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.myUserInfo;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.TaskVo;
import hewson.logindemo2.vo.UserVo;

public class myinfo_orderclassfier_activity extends AppCompatActivity implements View.OnClickListener {
    private List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
    TextView textview_topbar;
    Myadapter_orderclassfier myadapter_orderclassfier;
    ListView listview_empty;
    BootstrapButton button_seemore;
    TextView textview_note;
    ImageView imageview_nothing;

    String myresult;
    ServerResponse<List<TaskVo>> serverResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_orderclassfier);
        Log.e("未完成任务", "oncreate");
        Intent intent = getIntent();
        //0：啥都没有。1：未完成。2：已发布。3：已完成
        int flag = intent.getIntExtra("flag", 0);

        //更新list
        sendHttpRequest(flag);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ActivityCollectorUtil.addActivity(myinfo_orderclassfier_activity.this);
        textview_topbar = findViewById(R.id.textview_topbar);
        listview_empty = findViewById(R.id.listview_empty);
        button_seemore = findViewById(R.id.button_seemore);
        textview_note = findViewById(R.id.textview_note);
        imageview_nothing = findViewById(R.id.imageview_nothing);
        button_seemore.setOnClickListener(this);
        myadapter_orderclassfier = new Myadapter_orderclassfier(this, flag);

        //设置flag
//        setadapter(flag);

        initTopBar(flag);//设定顶部栏信息
    }


    //后台请求数据
    private void sendHttpRequest(int flag) {
        UserVo userVo = myUserInfo.getuser(this);
        String myuserid = String.valueOf(userVo.getUserid());
        String url = null;
        //0：啥都没有。1：未完成。2：已发布。3：已完成
        switch (flag) {
            case 1: {
                url = Const.IpAddress + "protal/Task/ShouldBeDone.do?finisherid=" + myuserid;
                if (url != null && url.length() != 0) {
                    OkhttpUtils.get(url,
                            new OkHttpCallback() {
                                @Override
                                public void OnFinish(String status, String msg) {
                                    super.OnFinish(status, msg);
                                    Gson gson = new Gson();
                                    serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<List<TaskVo>>>() {
                                    }.getType());
                                    List<TaskVo> taskVoList = serverResponse.getData();
                                    if (serverResponse.getStatus() == 28) {
                                        Message message = mHandler.obtainMessage();
                                        message.what = 3;
                                        message.obj = serverResponse.getMsg();
                                        mHandler.sendMessage(message);
                                    } else {
                                        listmap.clear();
                                        List<Map<String, Object>> listmapTemp = new ArrayList<Map<String, Object>>();
                                        for (TaskVo item : taskVoList) {
                                            Map<String, Object> map = new HashMap<String, Object>();
                                            if (!item.gettIsdone()) {
                                                map.put("TaskVo", item);
                                                listmapTemp.add(map);
                                            }
                                        }
                                        listmap.addAll(listmapTemp);

                                        Message msge = new Message();
                                        msge.what = 1;//setadapter,未完成
                                        msge.obj = listmap;
                                        mHandler.sendMessage(msge);
                                    }
                                }
                            }
                    );
                }
                break;
            }
            case 2: {
                url = Const.IpAddress + "protal/Task/MyPublished.do?publishid=" + myuserid;
                if (url != null && url.length() != 0) {
                    OkhttpUtils.get(url,
                            new OkHttpCallback() {
                                @Override
                                public void OnFinish(String status, String msg) {
                                    super.OnFinish(status, msg);
                                    Gson gson = new Gson();
                                    serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<List<TaskVo>>>() {
                                    }.getType());
                                    List<TaskVo> taskVoList = serverResponse.getData();
                                    if (serverResponse.getStatus() == 29) {
                                        imageview_nothing.setImageResource(R.mipmap.icon_nothing);
                                        textview_note.setText(serverResponse.getMsg());
                                    } else {
                                        listmap.clear();
                                        List<Map<String, Object>> listmapTemp = new ArrayList<Map<String, Object>>();
                                        for (TaskVo item : taskVoList) {
                                            Map<String, Object> map = new HashMap<String, Object>();
                                            //已发布
                                            map.put("TaskVo", item);
                                            listmapTemp.add(map);
                                        }
                                        listmap.addAll(listmapTemp);

                                        Message msge = new Message();
                                        msge.what = 2;//setadapter，已发布
                                        msge.obj = listmap;
                                        mHandler.sendMessage(msge);
                                    }
                                }
                            }
                    );
                }
                break;
            }
            case 3:
                url = Const.IpAddress + "protal/Task/ShouldBeDone.do?finisherid=" + myuserid;
                if (url != null && url.length() != 0) {
                    OkhttpUtils.get(url,
                            new OkHttpCallback() {
                                @Override
                                public void OnFinish(String status, String msg) {
                                    super.OnFinish(status, msg);
                                    Gson gson = new Gson();
                                    serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<List<TaskVo>>>() {
                                    }.getType());
                                    List<TaskVo> taskVoList = serverResponse.getData();
                                    if (serverResponse.getStatus() == 28) {
                                        Message messg = mHandler.obtainMessage();
                                        messg.what = 3;
                                        messg.obj = serverResponse.getMsg();
                                        mHandler.sendMessage(messg);
                                    } else {
                                        listmap.clear();
                                        List<Map<String, Object>> listmapTemp = new ArrayList<Map<String, Object>>();
                                        for (TaskVo item : taskVoList) {
                                            Map<String, Object> map = new HashMap<String, Object>();
                                            if (item.gettIsdone() ) {
                                                map.put("TaskVo", item);
                                                listmapTemp.add(map);
                                            }
                                        }
                                        listmap.addAll(listmapTemp);
                                        Message msge = new Message();
                                        msge.what = 4;//作为接受者已完成的任务
                                        msge.obj = listmap;
                                        mHandler.sendMessage(msge);
                                    }
                                }
                            }
                    );
                }
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://未完成
                    List<Map<String, Object>> list = (List<Map<String, Object>>) msg.obj;
                    if (listmap.size() == 0) {
                        imageview_nothing.setImageResource(R.mipmap.icon_nothing);
                        textview_note.setText("空空如也~");
                    } else {
                        myadapter_orderclassfier.setList(list);
                        myadapter_orderclassfier.notifyDataSetChanged();
                        listview_empty.setAdapter(myadapter_orderclassfier);
                        listview_empty.setOnItemClickListener(
                                new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Bundle bundle = new Bundle();
                                        Map<String, Object> map = (Map<String, Object>) myadapter_orderclassfier.getItem(position);
                                        TaskVo taskVo = (TaskVo) map.get("TaskVo");
                                        Gson gson=new Gson();
                                        String taskStr=gson.toJson(taskVo,new TypeToken<TaskVo>(){}.getType());
                                        bundle.putString("TaskVo",taskStr);
                                        //把参数放到bundle，实现activity传参
                                        bundle.putInt("item_userAvator", R.mipmap.icon_avatar);
                                        bundle.putString("item_userName", taskVo.gettDetail());
                                        bundle.putString("item_orderTitle", taskVo.gettTitle());
                                        bundle.putString("distance", "");
                                        bundle.putString("address", taskVo.gettAddress());
                                        bundle.putString("money", String.valueOf(taskVo.gettMoney()));
                                        bundle.putString("taskid", String.valueOf(taskVo.getTaskid()));

                                        Intent intent = new Intent(myinfo_orderclassfier_activity.this, OrderDone.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                }
                        );
                    }
                    break;
                case 2://已发布
                    if (listmap.size() == 0) {
                        imageview_nothing.setImageResource(R.mipmap.icon_nothing);
                        textview_note.setText("空空如也~");
                    } else {
                        myadapter_orderclassfier.setList(listmap);
                        myadapter_orderclassfier.notifyDataSetChanged();
                        listview_empty.setAdapter(myadapter_orderclassfier);
                        listview_empty.setOnItemClickListener(
                                new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Bundle bundle = new Bundle();
                                        Map<String, Object> map = (Map<String, Object>) myadapter_orderclassfier.getItem(position);
                                        TaskVo taskVo = (TaskVo) map.get("TaskVo");
                                        Gson gson=new Gson();
                                        String taskStr=gson.toJson(taskVo,new TypeToken<TaskVo>(){}.getType());
                                        bundle.putString("TaskVo",taskStr);
                                        //把参数放到bundle，实现activity传参
                                        bundle.putInt("item_userAvator", R.mipmap.icon_avatar);
                                        bundle.putString("item_userName", taskVo.gettDetail());
                                        bundle.putString("item_orderTitle", taskVo.gettTitle());
                                        bundle.putString("distance", "");
                                        bundle.putString("address", taskVo.gettAddress());
                                        bundle.putString("money", String.valueOf(taskVo.gettMoney()));
                                        bundle.putString("taskid", String.valueOf(taskVo.getTaskid()));
                                        bundle.putBoolean("isdone", taskVo.gettIsdone());
                                        Intent intent = new Intent(myinfo_orderclassfier_activity.this, OrderSendMoney.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                }
                        );
                    }
                    break;
                case 3://出现列表为空时，设定图片为404，并显示提示信息
                {
                    String note = (String) msg.obj;
                    imageview_nothing.setImageResource(R.mipmap.icon_nothing);
                    textview_note.setText(note);
                }
                break;
                case 4://已完成
                {
                    myadapter_orderclassfier.setList(listmap);
                    myadapter_orderclassfier.notifyDataSetChanged();
                    listview_empty.setAdapter(myadapter_orderclassfier);
                    listview_empty.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Bundle bundle = new Bundle();
                                    Map<String, Object> map = (Map<String, Object>) myadapter_orderclassfier.getItem(position);
                                    TaskVo taskVo = (TaskVo) map.get("TaskVo");
                                    Gson gson = new Gson();
                                    String taskStr = gson.toJson(taskVo);
                                    //把参数放到bundle，实现activity传参
                                    bundle.putString("taskVo", taskStr);

                                    bundle.putInt("item_userAvator", R.mipmap.icon_avatar);
                                    bundle.putString("item_userName", taskVo.gettDetail());
                                    bundle.putString("item_orderTitle", taskVo.gettTitle());
                                    bundle.putString("distance", "");
                                    bundle.putString("address", taskVo.gettAddress());
                                    bundle.putString("money", String.valueOf(taskVo.gettMoney()));
                                    bundle.putString("taskid", String.valueOf(taskVo.getTaskid()));
                                    bundle.putBoolean("isdone", taskVo.gettIsdone());
                                    Intent intent = new Intent(myinfo_orderclassfier_activity.this, OrderMyDone.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }
                    );
                }
                break;
            }
        }
    };

    private void initTopBar(int flag) {
        switch (flag) {
            //0：啥都没有。1：未完成。2：已发布。3：已完成
            case 1:
                textview_topbar.setText("未完成任务");
                break;
            case 2:
                textview_topbar.setText("已发布任务");
                break;
            case 3:
                textview_topbar.setText("已完成任务");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(myinfo_orderclassfier_activity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_seemore:
                //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
                Intent intent = new Intent(myinfo_orderclassfier_activity.this, home_activity.class);
                myinfo_orderclassfier_activity.this.startActivity(intent);
                //关闭当前activity
                myinfo_orderclassfier_activity.this.finish();
                break;
        }
    }
}
