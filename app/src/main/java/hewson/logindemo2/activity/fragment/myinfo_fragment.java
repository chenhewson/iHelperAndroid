package hewson.logindemo2.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.login_activity;
import hewson.logindemo2.activity.myinfo_orderclassfier_activity;
import hewson.logindemo2.activity.register_activity;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.SharePreferencesUtil;
import hewson.logindemo2.vo.UserVo;

public class myinfo_fragment extends Fragment implements View.OnClickListener{
    TextView textview_email;
    TextView textview_username;
    ImageView imageview_avator;
    BootstrapButton exit_login;

    ImageView imageview_no_done;
    ImageView imageview_published;
    ImageView imageview_done;
    ImageView imageview_money;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_myinfo,container,false);
        textview_email=view.findViewById(R.id.textview_email);
        textview_username=view.findViewById(R.id.textview_username);
        imageview_avator=view.findViewById(R.id.imageview_avator);
        exit_login=view.findViewById(R.id.button_exit);

        imageview_no_done=view.findViewById(R.id.imageview_no_done);
        imageview_published=view.findViewById(R.id.imageview_published);
        imageview_done=view.findViewById(R.id.imageview_done);
        imageview_money=view.findViewById(R.id.imageview_money);

        exit_login.setOnClickListener(this);
        imageview_no_done.setOnClickListener(this);
        imageview_published.setOnClickListener(this);
        imageview_done.setOnClickListener(this);
        imageview_money.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //判断用户是否登录,Activity本身就是一个Context，可以直接作为参数
        boolean isLogin = SharePreferencesUtil.getSharePreferencesInstance(getActivity()).readBoolean("isLogin");
        if (isLogin) {
            //已经登录,将json解析成UserVo实体类
            UserVo userVo = (UserVo) SharePreferencesUtil.getSharePreferencesInstance(getActivity()).readObject("user", UserVo.class);
            textview_username.setText(userVo.getUsername());
            textview_email.setText("E-mail:"+userVo.gettEmail());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_exit:
                SharePreferencesUtil util=SharePreferencesUtil.getSharePreferencesInstance(getActivity());
                util.delete("user");
                util.delete("isLogin");

                //腾讯聊天登出
                TIMManager.getInstance().logout(new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {

                        //错误码 code 和错误描述 desc，可用于定位请求失败原因
                        //错误码 code 列表请参见错误码表
                        Log.i("tencentINFO", "logout failed. code: " + code + " errmsg: " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("tencentINFO", "登出成功！");
                    }
                });
                ActivityCollectorUtil.finishAllActivity();
                //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
                Intent intent=new Intent(getActivity(), login_activity.class);
                getActivity().startActivity(intent);
                break;

            //0：啥都没有。1：未完成。2：已发布。3：已完成
            case R.id.imageview_no_done:
                getActivity().startActivity(initIntent(1));
                getActivity().finish();
                break;
            case R.id.imageview_published:
                getActivity().startActivity(initIntent(2));
                break;
            case R.id.imageview_done:
                getActivity().startActivity(initIntent(3));
                break;
            case R.id.imageview_money:
                break;
        }
    }

    public Intent initIntent(int flag){
        Intent intent=new Intent(getActivity(), myinfo_orderclassfier_activity.class);
        intent.putExtra("flag",flag);
        return intent;
    }
}