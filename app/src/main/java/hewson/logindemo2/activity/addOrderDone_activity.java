package hewson.logindemo2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;
import hewson.logindemo2.utils.ActivityCollectorUtil;

public class addOrderDone_activity extends AppCompatActivity implements View.OnClickListener {
    private BootstrapButton button_seemore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtil.addActivity(addOrderDone_activity.this);
        //隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_addorder_done);
        button_seemore=findViewById(R.id.button_seemore);
        button_seemore.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(addOrderDone_activity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_seemore:
                //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
                Intent intent=new Intent(addOrderDone_activity.this,home_activity.class);
                addOrderDone_activity.this.startActivity(intent);
                //关闭当前activity
                addOrderDone_activity.this.finish();
                break;
        }
    }
}
