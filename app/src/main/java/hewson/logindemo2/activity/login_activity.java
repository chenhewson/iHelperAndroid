package hewson.logindemo2.activity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hewson.logindemo2.R;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.UserVo;

public class login_activity extends AppCompatActivity implements View.OnClickListener {
    private EditText username_etittext;
    private EditText password_etittext;
    private Button login_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //获取用户名
        username_etittext=(EditText)findViewById(R.id.login_username);
        password_etittext=(EditText)findViewById(R.id.login_password);
        login_button=(Button)findViewById((R.id.login_button));

        //注册点击事件
        login_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                String username=username_etittext.getText().toString();
                String password=password_etittext.getText().toString();

                //okhttp请求
                OkhttpUtils.get(Const.IpAddress+"protal/user/login.do?username="+username+"&password="+password,new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);

                        //解析数据,将json格式的msg转为ServerResponse对象
                        Gson gson = new Gson();

                        //将泛型解析成UserVo对象：new TypeToken<ServerResponse<UserVo>>(){}.getType()
                        ServerResponse<UserVo> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<UserVo>>(){}.getType());

                        //Toast是子线程，所以要加Looper.prepare()和Looper.loop()，否则报错
                        Looper.prepare();
                        Toast.makeText(login_activity.this,serverResponse.getData().getUsername(), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                });
        }
    }
}
