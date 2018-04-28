package cool.monkey.android.websocketclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cool.monkey.android.websocketclient.R;
import cool.monkey.android.websocketclient.adapter.MessageAdapter;
import cool.monkey.android.websocketclient.bean.MessageBean;
import cool.monkey.android.websocketclient.service.WebsocketServer;
import cool.monkey.android.websocketclient.utils.MessageBeanUtils;

public class MainActivity extends AppCompatActivity implements WebsocketServer.WebsocketServerListener, View.OnLayoutChangeListener {

    @BindView(R.id.rv_message_main_activity) RecyclerView mRecyclerView;
    @BindView(R.id.edt_input_message_main_activity) EditText mEditText;
    @BindView(R.id.ll_input_main_activity) LinearLayout mInputLinearLayout;
    @BindView(R.id.ll_message_main_activity) LinearLayout mMainActivity;

    private List<MessageBean> messageBeanList = new ArrayList<>();
    private static CountDownTimer countDownTimer;
    private MessageAdapter messageAdapter;
    private int screenHeight = 0;
    private int keyHeight = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        starWebSocketService();
        WebsocketServer.setWebsocketServerListener(this);
        initRecyclerView();
        initEditTextSendClick();
        sendHeartbeat();
    }

    public void initEditTextSendClick() {
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(mEditText.getText().toString())) {
                    if (WebsocketServer.isWebSocketClientAvaliable()) {
                        MessageBean messageBean = new MessageBean();
                        messageBean.setCreateAt(System.currentTimeMillis());
                        messageBean.setSendUserId(2);
                        messageBean.setUserId(2);
                        messageBean.setMessage(mEditText.getText().toString());
                        messageBeanList.add(messageBean);
                        MessageBeanUtils.getInstance().insertOneData(messageBean);
                        initRecyclerView();
                        WebsocketServer.client.send(mEditText.getText().toString());
                        mEditText.setText("");
                    } else {
                        LogUtils.d("not connect");
                        WebsocketServer.connectServer();
                    }
                }
                return true;
            }
        });
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
    }

    public void initRecyclerView() {
        if (mRecyclerView != null) {
            if (messageAdapter == null && mRecyclerView != null) {
                messageBeanList = MessageBeanUtils.getInstance().queryAllData();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                messageAdapter = new MessageAdapter(messageBeanList, this);
                mRecyclerView.setAdapter(messageAdapter);
            } else if (messageAdapter != null && mRecyclerView != null) {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (messageAdapter != null) {
                            messageAdapter.notifyDataSetChanged();
                            mRecyclerView.smoothScrollToPosition((messageAdapter.getItemCount() - 1));
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onReceiveTextChatMessage(String message) {
        LogUtils.d("MainActivity onReceiveTextChatMessage message = " + message);
        MessageBean messageBean = new MessageBean();
        messageBean.setCreateAt(System.currentTimeMillis());
        messageBean.setSendUserId(1);
        messageBean.setUserId(2);
        messageBean.setMessage(message);
        messageBeanList.add(messageBean);
        MessageBeanUtils.getInstance().insertOneData(messageBean);
        initRecyclerView();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            if (messageAdapter != null && messageAdapter.getItemCount() > 1) {
                mRecyclerView.smoothScrollToPosition((messageAdapter.getItemCount() - 1));
            }
        }
    }

    public void starWebSocketService() {
        try {
            Intent startIntent = new Intent(this, WebsocketServer.class);
            startService(startIntent);
        } catch (Exception e) {

        }
    }

    public void sendHeartbeat() {
        try {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            countDownTimer = new CountDownTimer(20 * 1000, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    if (WebsocketServer.isWebSocketClientAvaliable()) {
                        WebsocketServer.client.sendPing();
                    } else {
                        starWebSocketService();
                    }
                    sendHeartbeat();
                }
            }.start();
        } catch (Exception e) {
            LogUtils.d("FriendsFragment sendHeartbeat failed Exception = " + e);
        }
    }
}
