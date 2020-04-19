package hewson.logindemo2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.chat.ChatActivity;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.Constants;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.SharePreferencesUtil;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
//隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //获取item布局中的组件的id
        item_userAvator=(ImageView)findViewById(R.id.detail_userAvator);
        item_userName=(TextView)findViewById(R.id.detail_userName);
        item_orderTitle=(TextView)findViewById(R.id.detail_orderTitle);
        item_money=(TextView)findViewById(R.id.detail_money);
        item_distinct=(TextView)findViewById(R.id.detail_distinct);
        item_distance=(TextView)findViewById(R.id.detail_distance);

        button_start_message=(BootstrapButton)findViewById(R.id.button_start_message);
        button_start_message.setOnClickListener(this);

        Bundle bundle=getIntent().getExtras();

        //获取保存在SharePreferences中当前登录的user
        final SharePreferencesUtil util=SharePreferencesUtil.getSharePreferencesInstance(OrderDetail.this);
        String userinfo=util.readString("user");

        //解析json，转为user实体类
        Gson gson=new Gson();
        final UserVo userVo=gson.fromJson(userinfo, UserVo.class);

        //从bundle获取参数
        item_userAvator.setImageResource(bundle.getInt("item_userAvator"));
        item_userName.setText((String)bundle.getString("item_userName"));
        item_orderTitle.setText((String)bundle.getString("item_orderTitle"));
        item_money.setText((String)bundle.getString("money"));
        item_distinct.setText((String)bundle.getString("address"));
        item_distance.setText((String)bundle.getString("distance"));

        String taskid=bundle.getString("taskid");
        Integer finisherid=userVo.getUserid();

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
        }
    }
}
