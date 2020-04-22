package hewson.logindemo2.utils;

import android.content.Context;

import com.google.gson.Gson;

import hewson.logindemo2.vo.UserVo;

public class getUserInfo {
    public static UserVo getuser(Context context){
        //获取保存在SharePreferences中当前登录的user
        SharePreferencesUtil util=SharePreferencesUtil.getSharePreferencesInstance(context);
        String userinfo=util.readString("user");
        //解析json，转为user实体类
        Gson gson=new Gson();
        UserVo userVo=gson.fromJson(userinfo, UserVo.class);
        return userVo;
    }
}
