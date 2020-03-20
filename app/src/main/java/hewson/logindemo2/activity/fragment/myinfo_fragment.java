package hewson.logindemo2.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hewson.logindemo2.R;
import hewson.logindemo2.utils.SharePreferencesUtil;
import hewson.logindemo2.vo.UserVo;

public class myinfo_fragment extends Fragment {
    TextView textview_email;
    TextView textview_username;
    ImageView imageview_avator;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_myinfo,container,false);
        textview_email=view.findViewById(R.id.textview_email);
        textview_username=view.findViewById(R.id.textview_username);
        imageview_avator=view.findViewById(R.id.imageview_avator);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //判断用户是否登录,Activity本身就是一个Context，可以直接作为参数
        boolean isLogin = SharePreferencesUtil.getSharePreferencesInstance(getActivity()).readBoolean("isLogin");
        if (isLogin) {
            //已经登录
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
}