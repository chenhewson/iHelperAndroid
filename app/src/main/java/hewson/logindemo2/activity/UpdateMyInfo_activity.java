package hewson.logindemo2.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.myUserInfo;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.UserVo;

public class UpdateMyInfo_activity extends AppCompatActivity implements View.OnClickListener {
    EditText edittext_tell;
    EditText edittext_email;
    BootstrapButton confirm_button;
    BootstrapButton goback_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatemyinfo);
        ActivityCollectorUtil.addActivity(this);
        //隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        initMyView();

    }

    private void initMyView() {
        edittext_tell=findViewById(R.id.edittext_tell);
        edittext_email=findViewById(R.id.edittext_email);
        confirm_button=findViewById(R.id.confirm_button);
        goback_button=findViewById(R.id.goback_button);

        confirm_button.setOnClickListener(this);
        goback_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm_button:
                UserVo userVo= myUserInfo.getuser(UpdateMyInfo_activity.this);
                String UpdateMyInfoUrl=Const.IpAddress+"protal/user/updateMyInfo.do?userid="+userVo.getUserid()+"&tell="+edittext_tell.getText()+"&email="+edittext_email.getText();
                //okHttp
                OkhttpUtils.get(UpdateMyInfoUrl,new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);

                        //解析数据,将json格式的msg转为ServerResponse对象
                        Gson gson = new Gson();

                        //不解析对象
                        ServerResponse serverResponse = gson.fromJson(msg,ServerResponse.class);

                        Message message=mHandler.obtainMessage();
                        message.what=1;
                        message.obj=serverResponse.getMsg();
                        mHandler.sendMessage(message);

                    }
                });
                break;
            case R.id.goback_button:{
                UpdateMyInfo_activity.this.finish();
            }break;
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    Toast.makeText(UpdateMyInfo_activity.this,(String)msg.obj,Toast.LENGTH_LONG).show();
                    UpdateMyInfo_activity.this.finish();
                }break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
    }
}
