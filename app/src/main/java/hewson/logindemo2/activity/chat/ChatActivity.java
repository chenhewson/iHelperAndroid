package hewson.logindemo2.activity.chat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import java.util.Set;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.login_activity;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.Constants;

public class ChatActivity extends Activity {

    private static final String TAG = ChatActivity.class.getSimpleName();

    private ChatFragment mChatFragment;
    private ChatInfo mChatInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        ActivityCollectorUtil.addActivity(ChatActivity.this);
        chat(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent");
        super.onNewIntent(intent);
        chat(intent);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(ChatActivity.this);
    }

    private void chat(Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.i(TAG, "bundle: " + bundle + " intent: " + intent);
        if (bundle == null) {
            Uri uri = intent.getData();
            if (uri != null) {
                // 离线推送测试代码，oppo scheme url解析
                Set<String> set = uri.getQueryParameterNames();
                if (set != null) {
                    for (String key : set) {
                        String value = uri.getQueryParameter(key);
                        Log.i(TAG, "oppo push scheme url key: " + key + " value: " + value);
                    }
                }
            }
        } else {

            // 离线推送测试代码，华为和oppo可以通过在控制台设置打开应用内界面为ChatActivity来测试发送的ext数据
            String ext = bundle.getString("ext");
            Log.i(TAG, "huawei push custom data ext: " + ext);

            Set<String> set = bundle.keySet();
            if (set != null) {
                for (String key : set) {
                    String value = bundle.getString(key);
                    Log.i(TAG, "oppo push custom data key: " + key + " value: " + value);
                }
            }
            // 离线推送测试代码结束

            mChatInfo = (ChatInfo) bundle.getSerializable(Constants.CHAT_INFO);
            if (mChatInfo == null) {
                return;
            }
            mChatFragment = new ChatFragment();
            mChatFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.empty_view, mChatFragment).commitAllowingStateLoss();
        }
    }

}
