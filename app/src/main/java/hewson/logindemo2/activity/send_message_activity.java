package hewson.logindemo2.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;

import hewson.logindemo2.utils.ActivityCollectorUtil;

public class send_message_activity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtil.addActivity(send_message_activity.this);
        //获取单聊会话
        String peer = "admin2";  //获取与用户 "admin2" 的会话
        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C,    //会话类型：单聊
                peer);

        //构造一条消息
        TIMMessage msg = new TIMMessage();

//添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText("我是消息测试");

//将elem添加到消息
        if(msg.addElement(elem) != 0) {
            Log.d("tencentINFO", "addElement failed");
            return;
        }

//发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.d("tencentINFO", "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e("tencentINFO", "SendMsg ok");
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(send_message_activity.this);
    }
}
