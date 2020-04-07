package hewson.logindemo2.application;


import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.beardedhen.androidbootstrap.TypefaceProvider;

public class bootstrapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
        //百度地图SDK的全局初始化
        SDKInitializer.initialize(getApplicationContext());
    }
}
