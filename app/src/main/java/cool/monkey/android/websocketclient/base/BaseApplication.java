package cool.monkey.android.websocketclient.base;

import android.app.Application;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

import cool.monkey.android.websocketclient.callback.ForegroundCallbacks;
import cool.monkey.android.websocketclient.service.CustomWebsocketServer;
import cool.monkey.android.websocketclient.utils.MessageBeanUtils;

public class BaseApplication extends Application {

    private static BaseApplication INSTANCE;

    public static BaseApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Utils.init(this);
        initAppStatusListener();
        MessageBeanUtils.Init(this);
    }

    /**
     * 初始化应用前后台状态监听
     */
    private void initAppStatusListener() {
        ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {
                LogUtils.d("TextChatManagerService initAppStatusListener CCApplication =  connectServer ");
                if (CustomWebsocketServer.client == null) {
                    starWebSocketService();
                } else {
                    CustomWebsocketServer.connectServer();
                }
            }

            @Override
            public void onBecameBackground() {

            }
        });
    }

    public void starWebSocketService() {
        try {
            Intent startIntent = new Intent(this, CustomWebsocketServer.class);
            startService(startIntent);
        } catch (Exception e) {

        }
    }
}
