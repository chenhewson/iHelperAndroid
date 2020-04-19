package hewson.logindemo2.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import hewson.logindemo2.R;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.GenerateTestUserSig;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.SharePreferencesUtil;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.UserVo;

public class login_activity extends AppCompatActivity implements View.OnClickListener {
    private EditText username_etittext;
    private EditText password_etittext;
    private BootstrapButton bootstrapButton;
    private BootstrapButton register_button;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActivityCollectorUtil.addActivity(login_activity.this);
        //隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        //获取用户名
        username_etittext=(EditText)findViewById(R.id.login_username);
        password_etittext=(EditText)findViewById(R.id.login_password);
        bootstrapButton=(BootstrapButton)findViewById((R.id.login_button));
        register_button=(BootstrapButton)findViewById((R.id.register_button));

        //注册点击事件
        bootstrapButton.setOnClickListener(this);
        register_button.setOnClickListener(this);

        //初始化 IM SDK 基本配置
        //判断是否是在主线程
        if (SessionWrapper.isMainProcess(getApplicationContext())) {
            TIMSdkConfig config = new TIMSdkConfig(Const.SDKAPPID)
                    .enableLogPrint(true)
                    .setLogLevel(TIMLogLevel.DEBUG)
                    .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

            //初始化 SDK
            TIMManager.getInstance().init(getApplicationContext(), config);
            Log.i("tencentINFO","初始化成功！");
        }

        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.i("tencentINFO", "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新 userSig 重新登录 IM SDK
                        Log.i("tencentINFO", "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i("tencentINFO", "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i("tencentINFO", "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i("tencentINFO", "onWifiNeedAuth");
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        Log.i("tencentINFO", "onGroupTipsEvent, type: " + elem.getTipsType());
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("tencentINFO", "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.i("tencentINFO", "onRefreshConversation, conversation size: " + conversations.size());
                    }
                });
        //禁用本地所有存储,这个如果开着会导致聊天界面读取不到消息
//        userConfig.disableStorage();
        //开启消息已读回执
        userConfig.enableReadReceipt(true);
        //将用户配置与通讯管理器进行绑定
        TIMManager.getInstance().setUserConfig(userConfig);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                final String username=username_etittext.getText().toString();
                String password=password_etittext.getText().toString();

                //okhttp请求
                OkhttpUtils.get(Const.IpAddress+"protal/user/login.do?username="+username+"&password="+password,
                        new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);

                        //解析数据,将json格式的msg转为ServerResponse对象
                        Gson gson = new Gson();

                        //将泛型解析成UserVo对象：new TypeToken<ServerResponse<UserVo>>(){}.getType()
                        ServerResponse<UserVo> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<UserVo>>(){}.getType());

                        //保存登录信息,数据存储SharedPreferences
                        if(serverResponse.getStatus()==0){

                            //调用SharePreferences工具类，将用户信息保存成文件.
                            SharePreferencesUtil util=SharePreferencesUtil.getSharePreferencesInstance(login_activity.this);
                            util.delete("user");
                            util.delete("isLogin");
                            util.putBoolean("isLogin",true);
                            util.putString("user",gson.toJson(serverResponse.getData()));
                            System.out.println(msg);

                            //腾讯聊天生成私钥
                            tencentLogin(username);


//                            //Toast是子线程，所以要加Looper.prepare()和Looper.loop()，否则报错
                            Looper.prepare();
                            Toast.makeText(login_activity.this,"欢迎！", Toast.LENGTH_SHORT).show();

                            //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
                            Intent intent=new Intent(login_activity.this,home_activity.class);
                            login_activity.this.startActivity(intent);
                            //关闭当前activity
                            login_activity.this.finish();
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            Toast.makeText(login_activity.this,serverResponse.getMsg(), Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                });


                break;

            case R.id.register_button:
                //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
                Intent intent=new Intent(login_activity.this,register_activity.class);
                login_activity.this.startActivity(intent);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(login_activity.this);
    }

    public void tencentLogin(String username){
        //腾讯聊天生成私钥
        String userSig=GenerateTestUserSig.genTestUserSig(username);
        // identifier 为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login(username, userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.i("tencentINFO", "login failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                Log.i("tencentINFO", "login succ");
            }
        });
    }

}
