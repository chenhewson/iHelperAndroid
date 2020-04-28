package hewson.logindemo2.application;


import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
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

        //七牛云
        Configuration config = new Configuration.Builder()
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
//                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone0)        // 设置区域，不指定会自动选择。指定不同区域的上传域名、备用域名、备用IP。
                .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
//        UploadManager uploadManager = new UploadManager(config, 3);//配置3个线程数并发上传；不配置默认为3，只针对file.size>4M生效。线程数建议不超过5，上传速度主要取决于上行带宽，带宽很小的情况单线程和多线程没有区别
    }
}
