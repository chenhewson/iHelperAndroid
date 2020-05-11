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
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.UserVo;

public class register_activity extends AppCompatActivity implements View.OnClickListener {
    private EditText register_usernameEdit;
    private EditText register_passwordEdit;
    private EditText register_emailEdit;
    private BootstrapButton confirmRegister_button;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityCollectorUtil.addActivity(this);
        //隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        //获取用户名
        register_usernameEdit=(EditText)findViewById(R.id.register_username);
        register_passwordEdit=(EditText)findViewById(R.id.register_password);
        register_emailEdit=(EditText)findViewById(R.id.register_email);
        confirmRegister_button=(BootstrapButton)findViewById((R.id.confirmRegister_button));

        //注册点击事件
        confirmRegister_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirmRegister_button:
                String username=register_usernameEdit.getText().toString();
                String password=register_passwordEdit.getText().toString();
                String Email=register_emailEdit.getText().toString();
                String RegisterUrl=Const.IpAddress+"protal/user/register.do?username="+username+"&password="+password+"&tEmail="+Email;
                //okHttp
                OkhttpUtils.get(RegisterUrl,new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);

                        //解析数据,将json格式的msg转为ServerResponse对象
                        Gson gson = new Gson();

                        //不解析对象
                        ServerResponse serverResponse = gson.fromJson(msg,ServerResponse.class);

                        //注册成功，跳回登录界面
                        if(serverResponse.getStatus()==10){
                            Looper.prepare();
                            Toast.makeText(register_activity.this,serverResponse.getMsg(), Toast.LENGTH_SHORT).show();

                            //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
                            Intent intent=new Intent(register_activity.this,login_activity.class);
                            register_activity.this.startActivity(intent);
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            Toast.makeText(register_activity.this,serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
    }
}
