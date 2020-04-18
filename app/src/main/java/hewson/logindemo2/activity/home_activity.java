package hewson.logindemo2.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.fragment.addorder_fragment;
import hewson.logindemo2.activity.fragment.home_fragment;
import hewson.logindemo2.activity.fragment.message_fragment;
import hewson.logindemo2.activity.fragment.myinfo_fragment;
import hewson.logindemo2.activity.fragment.star_fragment;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.BDLocationUtils;

public class home_activity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout bottom_home_linearlayout;
    LinearLayout bottom_star_linearlayout;
    LinearLayout bottom_addorder_linearlayout;
    LinearLayout bottom_message_linearlayout;
    LinearLayout bottom_myinfo_linearlayout;


    public static String HOME_FRAGMENT_TAG="HOME_FRAGMENT_TAG";
    public static String star_FRAGMENT_TAG="star_FRAGMENT_TAG";
    public static String addorder_FRAGMENT_TAG="addorder_FRAGMENT_TAG";
    public static String message_FRAGMENT_TAG="message_FRAGMENT_TAG";
    public static String myinfo_FRAGMENT_TAG="myinfo_FRAGMENT_TAG";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(home_activity.this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtil.addActivity(home_activity.this);
        setContentView(R.layout.activity_home);

        //隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        //启动就进入首页
        attachFragment(HOME_FRAGMENT_TAG);

        bottom_home_linearlayout=(LinearLayout)findViewById(R.id.bottom_home);
        bottom_star_linearlayout=(LinearLayout)findViewById(R.id.bottom_star);
        bottom_addorder_linearlayout=(LinearLayout)findViewById(R.id.bottom_addorder);
        bottom_message_linearlayout=(LinearLayout)findViewById(R.id.bottom_message);
        bottom_myinfo_linearlayout=(LinearLayout)findViewById(R.id.bottom_myinfo);

        //注册点击事件
        bottom_home_linearlayout.setOnClickListener(this);
        bottom_star_linearlayout.setOnClickListener(this);
        bottom_addorder_linearlayout.setOnClickListener(this);
        bottom_message_linearlayout.setOnClickListener(this);
        bottom_myinfo_linearlayout.setOnClickListener(this);

        //获取经纬度
        BDLocationUtils bdLocationUtils = new BDLocationUtils(home_activity.this);
        bdLocationUtils.doLocation();//开启定位
        bdLocationUtils.mLocationClient.start();//开始定位，Const写死了
    }

    //activity关联fragment
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_home:
                resetAllico(HOME_FRAGMENT_TAG);
                attachFragment(HOME_FRAGMENT_TAG);
                break;

            case R.id.bottom_star:
                resetAllico(star_FRAGMENT_TAG);
                attachFragment(star_FRAGMENT_TAG);
                break;

            case R.id.bottom_addorder:
                resetAllico(addorder_FRAGMENT_TAG);
                attachFragment(addorder_FRAGMENT_TAG);
                break;

            case R.id.bottom_message:
                resetAllico(message_FRAGMENT_TAG);
                attachFragment(message_FRAGMENT_TAG);
                break;

            case R.id.bottom_myinfo:
                resetAllico(myinfo_FRAGMENT_TAG);
                attachFragment(myinfo_FRAGMENT_TAG);
                break;
        }
    }

    //fragment管理器关联activity
    private void attachFragment(String fragmentTag){
        //1.获取fragment管理器
        FragmentManager fragmentManager=getSupportFragmentManager();

        //2.开启事务
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        Fragment fragment=fragmentManager.findFragmentByTag(fragmentTag);

        if(fragment==null){
            switch (fragmentTag){
                case "HOME_FRAGMENT_TAG":
                    fragment=new home_fragment();
                    fragmentTransaction.add(fragment,fragmentTag);
                    break;
                case "star_FRAGMENT_TAG":
                    fragment=new star_fragment();
                    fragmentTransaction.add(fragment,fragmentTag);
                    break;
                case "addorder_FRAGMENT_TAG":
                    fragment=new addorder_fragment();
                    fragmentTransaction.add(fragment,fragmentTag);
                    break;
                case "message_FRAGMENT_TAG":
                    fragment=new message_fragment();
                    fragmentTransaction.add(fragment,fragmentTag);
                    break;
                case "myinfo_FRAGMENT_TAG":
                    fragment=new myinfo_fragment();
                    fragmentTransaction.add(fragment,fragmentTag);
                    break;
            }

        }

        //把fragment_home.xml里面的id为fragment_content的占位布局替换掉
        fragmentTransaction.replace(R.id.fragment_content,fragment,fragmentTag);
        Log.e("fragmentTransactionreplace","fragmentTransactionreplace执行");

        fragmentTransaction.commit();
    }

    private void resetAllico(String selected){
        ImageView home_icon;
        ImageView star_icon;
        ImageView addorder_icon;
        ImageView message_icon;
        ImageView me_icon;

        home_icon=(ImageView)findViewById(R.id.home_icon);
        star_icon=(ImageView)findViewById(R.id.star_icon);
        addorder_icon=(ImageView)findViewById(R.id.addorder_icon);
        message_icon=(ImageView)findViewById(R.id.message_icon);
        me_icon=(ImageView)findViewById(R.id.me_icon);

        home_icon.setImageResource(R.mipmap.icon_home_unselect);
        star_icon.setImageResource(R.mipmap.icon_star_unselect);
        addorder_icon.setImageResource(R.mipmap.icon_add_unselect);
        message_icon.setImageResource(R.mipmap.icon_message_unselect);
        me_icon.setImageResource(R.mipmap.icon_me_unselect);

        switch (selected){
            case "HOME_FRAGMENT_TAG":
                home_icon.setImageResource(R.mipmap.icon_home_selected);
                break;
            case "star_FRAGMENT_TAG":
                star_icon.setImageResource(R.mipmap.icon_star_selected);
                break;
            case "addorder_FRAGMENT_TAG":
                addorder_icon.setImageResource(R.mipmap.icon_add_selected);
                break;
            case "message_FRAGMENT_TAG":
                message_icon.setImageResource(R.mipmap.icon_message_selected);
                break;
            case "myinfo_FRAGMENT_TAG":
                me_icon.setImageResource(R.mipmap.icon_me_selected);
                break;
        }
    }

    /*暂停所有fragment*/
    public void pauseAllFragment(){
        
    }
}
