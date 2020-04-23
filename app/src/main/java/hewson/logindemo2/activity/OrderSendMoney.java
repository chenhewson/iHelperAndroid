package hewson.logindemo2.activity;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.chat.ChatActivity;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.Constants;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.myUserInfo;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.UserVo;

public class OrderSendMoney extends AppCompatActivity implements View.OnClickListener{
    private String taskUsername;
    //获取item布局中的组件的id
    ImageView item_userAvator;
    TextView item_userName;
    TextView item_orderTitle;
    TextView item_money;
    TextView item_distinct;
    TextView item_distance;
    TextView item_note;
    BootstrapButton button_start_message;
    ImageView imageview_heart;
    BootstrapButton button_order_sendmoney;
    String taskid;
    UserVo userVo=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sendmoney);
        ActivityCollectorUtil.addActivity(OrderSendMoney.this);
//隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //获取item布局中的组件的id
        item_userAvator=(ImageView)findViewById(R.id.detail_userAvator);
        item_userName=(TextView)findViewById(R.id.detail_userName);
        item_orderTitle=(TextView)findViewById(R.id.item_orderTitle);
        item_money=(TextView)findViewById(R.id.detail_money);
        item_distinct=(TextView)findViewById(R.id.detail_distinct);
        item_distance=(TextView)findViewById(R.id.detail_distance);
        item_note=(TextView)findViewById(R.id.item_note);
        imageview_heart=(ImageView)findViewById(R.id.imageview_heart);
        button_order_sendmoney=(BootstrapButton)findViewById(R.id.button_order_sendmoney);


        button_start_message=(BootstrapButton)findViewById(R.id.button_start_message);
        button_start_message.setOnClickListener(this);
        imageview_heart.setOnClickListener(this);
        button_order_sendmoney.setOnClickListener(this);

        Bundle bundle=getIntent().getExtras();

        //解析json，转为user实体类
        userVo= myUserInfo.getuser(OrderSendMoney.this);

        //从bundle获取参数
        item_userAvator.setImageResource(bundle.getInt("item_userAvator"));
        item_userName.setText((String)bundle.getString("item_userName"));
        item_orderTitle.setText((String)bundle.getString("item_orderTitle"));
        item_money.setText((String)bundle.getString("money"));
        item_distinct.setText((String)bundle.getString("address"));
        item_distance.setText((String)bundle.getString("distance"));
        item_note.setText("(请前往打赏)");

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
                            imageview_heart.setImageResource(R.mipmap.icon_heart);
                        }

                        //心愿清单已经存在该项
                        if(serverResponse.getStatus()==1){
                            imageview_heart.setImageResource(R.mipmap.icon_heart_selected);
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
                Intent intent = new Intent(OrderSendMoney.this, ChatActivity.class);//ChatActivity就是用于控制聊天界面的
                intent.putExtra(Constants.CHAT_INFO, chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                OrderSendMoney.this.startActivity(intent);
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
                                    imageview_heart.setImageResource(R.mipmap.icon_heart_selected);
                                    Looper.prepare();
                                    Toast.makeText(OrderSendMoney.this,"已加入心愿单！", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }

                                //心愿清单已经存在该项，现已移除该项目
                                if(serverResponse.getStatus()==1){
                                    imageview_heart.setImageResource(R.mipmap.icon_heart);
                                    Looper.prepare();
                                    Toast.makeText(OrderSendMoney.this,"已移除！", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }
                            }
                        });
                break;

            case R.id.button_order_sendmoney:
                Log.i("button_order_sendmoney","打钱");
                OkhttpUtils.get(Const.IpAddress+"protal/Task/ConfirmDone.do?taskid="+taskid,
                        new OkHttpCallback(){
                            @Override
                            public void OnFinish(String status, String msg) {
                                super.OnFinish(status, msg);
                                //解析数据,将json格式的msg转为ServerResponse对象
                                Gson gson = new Gson();

                                //将泛型解析成String对象：new TypeToken<ServerResponse<String>>(){}.getType()
                                ServerResponse<UserVo> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<UserVo>>(){}.getType());

                                Looper.prepare();
                                Toast.makeText(OrderSendMoney.this,serverResponse.getMsg(), Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                        });
                //跳转到任务接受成功界面
                OrderSendMoney.this.startActivity(new Intent(OrderSendMoney.this,ReceiveDone_activity.class));
                OrderSendMoney.this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(OrderSendMoney.this);
    }
}
