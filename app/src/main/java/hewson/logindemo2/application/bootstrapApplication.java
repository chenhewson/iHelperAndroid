package hewson.logindemo2.application;


import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;

import hewson.logindemo2.common.Const;

public class bootstrapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
        //百度地图SDK的全局初始化
        SDKInitializer.initialize(getApplicationContext());

        //腾讯聊天
        // 配置 Config，请按需配置
        TUIKitConfigs configs = TUIKit.getConfigs();
        configs.setSdkConfig(new TIMSdkConfig(Const.SDKAPPID));
        configs.setCustomFaceConfig(new CustomFaceConfig());
        configs.setGeneralConfig(new GeneralConfig());

        TUIKit.init(this, Const.SDKAPPID, configs);
    }
}
