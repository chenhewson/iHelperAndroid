package hewson.logindemo2.utils;
/*
* 用单例封装SharePreferences,用于将用户信息保存到本地
* */
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharePreferencesUtil {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //文件名包名
    private  static final String FILENAME="logindemo2";

    private static SharePreferencesUtil sharePreferencesUtil;

    private SharePreferencesUtil(Context context){
        sharedPreferences=context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public static SharePreferencesUtil getSharePreferencesInstance(Context context) {
        if (sharePreferencesUtil == null) {
            synchronized (SharePreferencesUtil.class) {
                if (sharePreferencesUtil == null) {
                    synchronized (SharePreferencesUtil.class){
                        sharePreferencesUtil = new SharePreferencesUtil(context);
                    }
                }
            }
        }
        return sharePreferencesUtil;
    }

    //保存布尔类型
    public void putBoolean(String key,boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }

    //保存字符串
    public void putString(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }

    //读boolean
    public boolean readBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }

    //读String
    public String readString(String key){
        return sharedPreferences.getString(key,"字符串君不见了");
    }

    //读对象
    public Object readObject(String key,Class clazz){
        String string= sharedPreferences.getString(key,"找不到对象");
        Gson gson=new Gson();
        return gson.fromJson(string,clazz);
    }

    //删除
    public void delete(String key){
        editor.remove(key).commit();
    }

    //清空
    public void clear(){
        sharePreferencesUtil.clear();
    }

    //自定义。。。
}
