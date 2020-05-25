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

import java.util.Random;

import hewson.logindemo2.R;
import hewson.logindemo2.application.TimeButton;
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
    private TimeButton timeButton;
    private EditText register_email_code;

    //邮箱验证码
    private int code;



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
        register_email_code=(EditText)findViewById(R.id.register_email_code);

        //注册点击事件
        confirmRegister_button.setOnClickListener(this);

        timeButton = (TimeButton) findViewById(R.id.sendCode_button);
        timeButton.onCreate(savedInstanceState);
        timeButton.setTextAfter("秒后重新验证").setTextBefore("点击发送验证码").setLenght(30 * 1000);//默认毫秒，现在是30秒
        timeButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirmRegister_button: {
                String edit_code = register_email_code.getText().toString();//用户输入的验证码
                String send_code = String.valueOf(code);
                if ((send_code.equals(edit_code)) && (edit_code.length() != 0 && edit_code != null) && (send_code.length() != 0 && send_code != null)) {
                    //验证正确，可以注册
                    String username = register_usernameEdit.getText().toString();
                    String password = register_passwordEdit.getText().toString();
                    String Email = register_emailEdit.getText().toString();
                    String RegisterUrl = Const.IpAddress + "protal/user/register.do?username=" + username + "&password=" + password + "&tEmail=" + Email;
                    //okHttp
                    OkhttpUtils.get(RegisterUrl, new OkHttpCallback() {
                        @Override
                        public void OnFinish(String status, String msg) {
                            super.OnFinish(status, msg);

                            //解析数据,将json格式的msg转为ServerResponse对象
                            Gson gson = new Gson();

                            //不解析对象
                            ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                            //注册成功，跳回登录界面
                            if (serverResponse.getStatus() == 10) {
                                Looper.prepare();
                                Toast.makeText(register_activity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();

                                //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
                                Intent intent = new Intent(register_activity.this, login_activity.class);
                                register_activity.this.startActivity(intent);
                                Looper.loop();
                            } else {
                                Looper.prepare();
                                Toast.makeText(register_activity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    });
                }else {
                    Toast.makeText(register_activity.this, "验证码输入错误！", Toast.LENGTH_SHORT).show();
                }
            }
                break;
            case R.id.sendCode_button:{
                //生成六位数随机码
                Random r=new Random();
                code=r.nextInt(900000)+100000;

                String sendCodeUrl=Const.IpAddress+"protal/user/sendEmail.do?emailAddress="+register_emailEdit.getText().toString()+"&emailCode="+String.valueOf(code);
                //okHttp
                OkhttpUtils.get(sendCodeUrl,new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);

                        //解析数据,将json格式的msg转为ServerResponse对象
                        Gson gson = new Gson();

                        //不解析对象
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);


                        Looper.prepare();
                        Toast.makeText(register_activity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
    }
}
