package hewson.logindemo2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hewson.logindemo2.R;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.ActivityCollectorUtil;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                String username=username_etittext.getText().toString();
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

                            //读文件中的boolean类型
                            boolean isLogin=util.readBoolean("isLogin");

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
                });break;

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

}
