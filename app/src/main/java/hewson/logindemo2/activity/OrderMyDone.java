package hewson.logindemo2.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import java.text.DateFormat;
import java.util.Date;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.chat.ChatActivity;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.Constants;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.myUserInfo;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.TaskVo;
import hewson.logindemo2.vo.UserVo;

public class OrderMyDone extends AppCompatActivity implements View.OnClickListener{
    private String taskUsername;
    //获取item布局中的组件的id
    TextView item_userName;
    TextView item_orderTitle;
    TextView item_money;
    TextView item_distinct;
    TextView item_distance;
    BootstrapButton button_start_message;
    BootstrapButton button_see_more;
    TextView textview_sendmoney_time;
    TextView textview_note;
    String taskid;
    UserVo userVo=null;
    TaskVo taskVo;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_my_done);
        initMyView();
        ActivityCollectorUtil.addActivity(OrderMyDone.this);
//隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        Bundle bundle=getIntent().getExtras();
        String taskStr=bundle.getString("taskVo");
        Gson gson=new Gson();
        taskVo=gson.fromJson(taskStr, new TypeToken<TaskVo>(){}.getType());

        //解析json，转为user实体类
        userVo= myUserInfo.getuser(OrderMyDone.this);

        //从bundle获取taskVo并设置
        item_userName.setText(taskVo.gettDetail());
        item_orderTitle.setText(taskVo.gettTitle());
        item_money.setText(String.valueOf(taskVo.gettMoney()));
        item_distinct.setText(taskVo.gettAddress());

        Date finishetime = taskVo.gettFinishtime();
        if(finishetime!=null){
            //格式化时间
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
            textview_sendmoney_time.setText(df.format(finishetime));
        }else {
            textview_sendmoney_time.setText("无法获得到账时间");
        }
        if(taskVo.gettIsdestroy()){
        }else {
            textview_note.setText("(发布方尚未确认)");
        }

        taskVo.gettIsdone();

        taskid=String.valueOf(taskVo.getTaskid());

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
    }

    private void initMyView() {
        //获取item布局中的组件的id
        textview_note=findViewById(R.id.textview_note);
        item_userName=(TextView)findViewById(R.id.detail_userName);
        item_orderTitle=(TextView)findViewById(R.id.detail_orderTitle);
        item_money=(TextView)findViewById(R.id.detail_money);
        item_distinct=(TextView)findViewById(R.id.detail_distinct);
        item_distance=(TextView)findViewById(R.id.detail_distance);
        textview_sendmoney_time=findViewById(R.id.textview_sendmoney_time);

        button_see_more=findViewById(R.id.button_see_more);
        button_start_message=(BootstrapButton)findViewById(R.id.button_start_message);
        button_start_message.setOnClickListener(this);
        button_see_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start_message:
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(TIMConversationType.C2C);//c2c为单聊模式
                chatInfo.setId(taskUsername);//单聊唯一标识,单聊模式为用户名
                chatInfo.setChatName(taskUsername);//用户名
                Intent intent = new Intent(OrderMyDone.this, ChatActivity.class);//ChatActivity就是用于控制聊天界面的
                intent.putExtra(Constants.CHAT_INFO, chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                OrderMyDone.this.startActivity(intent);
                break;
            case R.id.button_see_more:{
                //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
                OrderMyDone.this.startActivity(new Intent(OrderMyDone.this,home_activity.class));
                //关闭当前activity
                OrderMyDone.this.finish();
            }break;
        }
    }

    //加载图片工具类
    private void loadGlide(String mUrl,ImageView mImageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.icon_avatar)
                .error(R.mipmap.icon_avatar);
        Glide.with(this).load(mUrl).apply(requestOptions).into(mImageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(OrderMyDone.this);
    }
}
