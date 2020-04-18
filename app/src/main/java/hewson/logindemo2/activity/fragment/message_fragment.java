package hewson.logindemo2.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;

import hewson.logindemo2.R;

public class message_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_message,container,false);
        // 从布局文件中获取会话列表面板
        ConversationLayout conversationLayout = view.findViewById(R.id.conversation_layout);
        // 初始化聊天面板
        conversationLayout.initDefault();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
