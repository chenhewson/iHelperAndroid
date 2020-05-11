package hewson.logindemo2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.chat.ChatActivity;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.Constants;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.SharePreferencesUtil;
import hewson.logindemo2.utils.myUserInfo;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.UserVo;

public class OrderDetail extends AppCompatActivity implements View.OnClickListener{
    private String taskUsername;
    //获取item布局中的组件的id
    ImageView item_userAvator;
    TextView item_userName;
    TextView item_orderTitle;
    TextView item_money;
    TextView item_distinct;
    TextView item_distance;
    BootstrapButton button_start_message;
    ImageView imageview_heart;
    BootstrapButton button_detail_receiveorder;
    String taskid;
    UserVo userVo=null;
    MapView bmapView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ActivityCollectorUtil.addActivity(OrderDetail.this);
//隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //获取item布局中的组件的id
        initMyview();


        button_start_message.setOnClickListener(this);
        imageview_heart.setOnClickListener(this);
        button_detail_receiveorder.setOnClickListener(this);

        Bundle bundle=getIntent().getExtras();

        userVo= myUserInfo.getuser(OrderDetail.this);

        //从bundle获取参数
        item_userAvator.setImageResource(bundle.getInt("item_userAvator"));
        item_userName.setText((String)bundle.getString("item_userName"));
        item_orderTitle.setText((String)bundle.getString("item_orderTitle"));
        item_money.setText((String)bundle.getString("money"));
        item_distinct.setText((String)bundle.getString("address"));
        item_distance.setText((String)bundle.getString("distance"));

        //获取地理坐标
        Double jingdu=bundle.getDouble("jingdu");
        Double weidu=bundle.getDouble("weidu");
        LatLng GEO_LOCATION = new LatLng(weidu, jingdu);

        //设置地图中心
        MapStatusUpdate status1 = MapStatusUpdateFactory.newLatLng(GEO_LOCATION);
        MapStatusUpdate status2 = MapStatusUpdateFactory.zoomTo(18);
        bmapView.getMap().setMapStatus(status1);
        bmapView.getMap().setMapStatus(status2);

        // 构建markerOption，用于在地图上添加marker
        BitmapDescriptor bitmap=BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        OverlayOptions options = new MarkerOptions()//
                .position(GEO_LOCATION)// 设置marker的位置
                .icon(bitmap)// 设置marker的图标
                .zIndex(9)// 設置marker的所在層級
                .draggable(true);// 设置手势拖拽

        // 在地图上添加marker，并显示
        bmapView.getMap().addOverlay(options);


        taskid=bundle.getString("taskid");
        Integer finisherid=userVo.getUserid();

        //获取该任务发布者的用户名，作为腾讯聊天的聊天对象
        OkhttpUtils.get(Const.IpAddress+"protal/Task/getUsername.do?taskId="+taskid,
                new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);
                        //解析数据,将json格式的msg转为ServerResponse对象
                        Gson gson = new Gson();

                        //将泛型解析成String对象：new TypeToken<ServerResponse<String>>(){}.getType()
                        ServerResponse<String> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<String>>(){}.getType());

                        if(serverResponse.getStatus()==0){
                            String username=serverResponse.getData().replaceAll("\"","");
                            taskUsername=username;
                        }
                    }
                });

        OkhttpUtils.get(Const.IpAddress+"protal/Task/StarExist.do?tUserid="+String.valueOf(userVo.getUserid())+"&tTaskid="+String.valueOf(taskid),
                new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);
                        //解析数据,将json格式的msg转为ServerResponse对象
                        Gson gson = new Gson();

                        //将泛型解析成String对象：new TypeToken<ServerResponse<String>>(){}.getType()
                        ServerResponse<String> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<String>>(){}.getType());

                        //心愿清单不存在该项
                        if(serverResponse.getStatus()==0){
                            //设定红心为空心
                            Message message=mHandler.obtainMessage();
                            message.what=3;
                            mHandler.sendMessage(message);
                        }

                        //心愿清单已经存在该项
                        if(serverResponse.getStatus()==1){
                            //设定红心为实心
                            Message message=mHandler.obtainMessage();
                            message.what=4;
                            mHandler.sendMessage(message);
                        }
                    }
                });
    }

    private void initMyview() {
        item_userAvator=(ImageView)findViewById(R.id.detail_userAvator);
        item_userName=(TextView)findViewById(R.id.detail_userName);
        item_orderTitle=(TextView)findViewById(R.id.detail_orderTitle);
        item_money=(TextView)findViewById(R.id.detail_money);
        item_distinct=(TextView)findViewById(R.id.detail_distinct);
        item_distance=(TextView)findViewById(R.id.detail_distance);
        imageview_heart=(ImageView)findViewById(R.id.imageview_heart);
        button_detail_receiveorder=(BootstrapButton)findViewById(R.id.button_detail_receiveorder);

        button_start_message=(BootstrapButton)findViewById(R.id.button_start_message);
        bmapView=findViewById(R.id.bmapView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start_message:
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(TIMConversationType.C2C);//c2c为单聊模式
                chatInfo.setId(taskUsername);//单聊唯一标识,单聊模式为用户名
                chatInfo.setChatName(taskUsername);//用户名
                Intent intent = new Intent(OrderDetail.this, ChatActivity.class);//ChatActivity就是用于控制聊天界面的
                intent.putExtra(Constants.CHAT_INFO, chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                OrderDetail.this.startActivity(intent);
                break;

            case R.id.imageview_heart:
                //判断该任务是否在愿望清单
                Integer userid = userVo.getUserid();//tUserid
                OkhttpUtils.get(Const.IpAddress+"protal/Task/starExistTask.do?tUserid="+String.valueOf(userid)+"&tTaskid="+String.valueOf(taskid),
                        new OkHttpCallback(){
                            @Override
                            public void OnFinish(String status, String msg) {
                                super.OnFinish(status, msg);
                                //解析数据,将json格式的msg转为ServerResponse对象
                                Gson gson = new Gson();

                                //将泛型解析成String对象：new TypeToken<ServerResponse<String>>(){}.getType()
                                ServerResponse<String> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<String>>(){}.getType());

                                //心愿清单不存在该项，现已加入该项目
                                if(serverResponse.getStatus()==0){
                                    Message message=mHandler.obtainMessage();
                                    message.what=2;
                                    mHandler.sendMessage(message);
                                    Looper.prepare();
                                    Toast.makeText(OrderDetail.this,"已加入心愿单！", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }

                                //心愿清单已经存在该项，现已移除该项目
                                if(serverResponse.getStatus()==1){
                                    Message message=mHandler.obtainMessage();
                                    message.what=1;
                                    mHandler.sendMessage(message);
                                }
                            }
                        });
                break;

            case R.id.button_detail_receiveorder:
                Log.i("OrderDetail","接受任务执行");
                OkhttpUtils.get(Const.IpAddress+"protal/Task/receiveTask.do?finisherid="+String.valueOf(userVo.getUserid())+"&taskid="+String.valueOf(taskid),
                        new OkHttpCallback(){
                            @Override
                            public void OnFinish(String status, String msg) {
                                super.OnFinish(status, msg);
                                //解析数据,将json格式的msg转为ServerResponse对象
                                Gson gson = new Gson();

                                //将泛型解析成String对象：new TypeToken<ServerResponse<String>>(){}.getType()
                                ServerResponse<String> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<String>>(){}.getType());
                                Toast.makeText(OrderDetail.this,serverResponse.getMsg(), Toast.LENGTH_LONG).show();
                            }
                        });
                //跳转到任务接受成功界面
                OrderDetail.this.startActivity(new Intent(OrderDetail.this,ReceiveDone_activity.class));
                OrderDetail.this.finish();
                break;
        }
    }



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{//设定红心为空心
                    imageview_heart.setImageResource(R.mipmap.icon_heart);
                    Toast.makeText(OrderDetail.this,"已移除！", Toast.LENGTH_LONG).show();
                }break;
                case 2:{//设定红心为实心
                    imageview_heart.setImageResource(R.mipmap.icon_heart_selected);
                    Toast.makeText(OrderDetail.this,"已加入心愿单！", Toast.LENGTH_LONG).show();
                }break;
                case 3:{//设定红心为空心,无提示
                    imageview_heart.setImageResource(R.mipmap.icon_heart);
                }break;
                case 4:{//设定红心为实心，无提示
                    imageview_heart.setImageResource(R.mipmap.icon_heart_selected);
                }break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(OrderDetail.this);
        bmapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bmapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bmapView.onResume();
    }
}
