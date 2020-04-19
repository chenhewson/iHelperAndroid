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

import hewson.logindemo2.R;
import hewson.logindemo2.utils.SharePreferencesUtil;
import hewson.logindemo2.vo.UserVo;

public class OrderDetail extends AppCompatActivity implements View.OnClickListener{
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start_message:
                Bundle bundle = new Bundle();
                bundle.putString("userid",item_userName.getText().toString());
                Intent intent=new Intent(OrderDetail.this,send_message_activity.class);
                OrderDetail.this.startActivity(intent);
                break;
        }
    }
}
