package hewson.logindemo2.activity;

import android.os.Bundle;
import android.view.View;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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




    }

    //activity关联fragment
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_home:
                attachFragment(HOME_FRAGMENT_TAG);
                break;

            case R.id.bottom_star:
                attachFragment(star_FRAGMENT_TAG);
                break;

            case R.id.bottom_addorder:
                attachFragment(addorder_FRAGMENT_TAG);
                break;

            case R.id.bottom_message:
                attachFragment(message_FRAGMENT_TAG);
                break;

            case R.id.bottom_myinfo:
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

        //把gragment_home.xml里面的id为fragment_content的占位布局替换掉
        fragmentTransaction.replace(R.id.fragment_content,fragment,fragmentTag);

        fragmentTransaction.commit();


    }
}
