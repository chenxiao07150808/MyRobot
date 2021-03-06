package cn.edu.gdmec.s07150808.myapplication2;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.gdmec.s07150808.myapplication2.bean.ChatMessage;
import cn.edu.gdmec.s07150808.myapplication2.utils.HttpUtils;

public class MainActivity extends Activity {


    private ListView mChatView;

    private EditText mMsg;
     /*存储聊天记录*/
    private List<ChatMessage> mDatds = new ArrayList<ChatMessage>();
    /*适配器*/
    private ChatMessageAdapter mAdapter;

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            /*obj  ChatMessage存储数据处理*/
            ChatMessage from =(ChatMessage) msg.obj;
            mDatds.add(from);
            mAdapter.notifyDataSetChanged();
            mChatView.setSelection(mDatds.size() -1);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_chatting);
        initView();

        mAdapter = new ChatMessageAdapter(this, mDatds);
        mChatView.setAdapter(mAdapter);

    }
    private void initView()
    {
        mChatView = (ListView) findViewById(R.id.id_chat_listView);
        mMsg = (EditText) findViewById(R.id.id_chat_msg);
        mDatds.add(new ChatMessage(ChatMessage.Type.INPUR, "游戏才刚刚开始！"));
    }
    public void sendMessage(View view)
    {
        final String msg = mMsg.getText().toString();
        if (TextUtils.isEmpty(msg))
        {
            Toast.makeText(this, "您还没有填写信息呢...", Toast.LENGTH_SHORT).show();
            return;
        }

        ChatMessage to = new ChatMessage(ChatMessage.Type.OUTPUT, msg);
        to.setDate(new Date());
        mDatds.add(to);

        mAdapter.notifyDataSetChanged();
        mChatView.setSelection(mDatds.size() - 1);

        mMsg.setText("");

        // 关闭软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive())
        {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }

        new Thread()
        {
            public void run()
            {
                ChatMessage from = null;
                try
                {
                    from = HttpUtils.sendMsg(msg);
                } catch (Exception e)
                {
                    from = new ChatMessage(ChatMessage.Type.INPUR, "服务器挂了呢...");
                }
                Message message = Message.obtain();
                message.obj = from;
                handler.sendMessage(message);
            }
        }.start();

    }
}
