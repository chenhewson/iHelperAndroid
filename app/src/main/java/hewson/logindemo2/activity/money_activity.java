package hewson.logindemo2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;

import hewson.logindemo2.R;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.myUserInfo;
import hewson.logindemo2.vo.UserVo;

public class money_activity extends AppCompatActivity implements View.OnClickListener{
    TextView textview_balance;
    BootstrapButton goback_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        ActivityCollectorUtil.addActivity(this);
        //隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //注册点击事件，获取组件
        initMyView();

        myUserInfo.setuser(money_activity.this);
        UserVo userVo=myUserInfo.getuser(money_activity.this);
        textview_balance.setText(String.format("%.2f",userVo.gettBalance()));
    }

    private void initMyView() {
        textview_balance=findViewById(R.id.textview_balance);
        goback_button=findViewById(R.id.goback_button);

        goback_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goback_button:{
                //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
//                Intent intent=new Intent(money_activity.this,home_activity.class);
//                money_activity.this.startActivity(intent);
                //关闭当前activity
                money_activity.this.finish();
            }break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
    }
}
