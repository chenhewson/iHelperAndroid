package hewson.logindemo2.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hewson.logindemo2.activity.OrderSendMoney;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.vo.ServerResponse;
import hewson.logindemo2.vo.UserVo;

public class myUserInfo {
    public static UserVo getuser(Context context){
        //获取保存在SharePreferences中当前登录的user
        SharePreferencesUtil util=SharePreferencesUtil.getSharePreferencesInstance(context);
        String userinfo=util.readString("user");
        //解析json，转为user实体类
        Gson gson=new Gson();
        UserVo userVo=gson.fromJson(userinfo, UserVo.class);
        return userVo;
    }

    //更新user
    public static void setuser(Context context){
        final Context cont=context;
        UserVo userVo=getuser(cont);
        OkhttpUtils.get(Const.IpAddress+"protal/User/getUserinfo.do?userid="+userVo.getUserid(),
                new OkHttpCallback(){
                    @Override
                    public void OnFinish(String status, String msg) {
                        super.OnFinish(status, msg);
                        //解析数据,将json格式的msg转为ServerResponse对象
                        Gson gson = new Gson();
                        //将泛型解析成String对象：new TypeToken<ServerResponse<String>>(){}.getType()
                        ServerResponse<UserVo> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<UserVo>>(){}.getType());
                        UserVo userNew=serverResponse.getData();
                        if (userNew!=null){
                            //获取保存在SharePreferences中当前登录的user
                            SharePreferencesUtil util=SharePreferencesUtil.getSharePreferencesInstance(cont);
                            util.delete("user");
                            //解析json，转为user实体类
                            String userStr=gson.toJson(userNew);
                            util.putString("user",userStr);
                        }
                    }
                });

    }
}
