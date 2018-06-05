package cool.monkey.android.websocketclient.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.net.URI;

import cool.monkey.android.websocketclient.BuildConfig;
import cool.monkey.android.websocketclient.base.BaseApplication;
import cool.monkey.android.websocketclient.constans.ConstPath;
import cool.monkey.android.websocketclient.websocket.client.WebSocketClient;
import cool.monkey.android.websocketclient.websocket.drafts.Draft_6455;
import cool.monkey.android.websocketclient.websocket.handshake.ServerHandshake;

public class CustomWebsocketServer extends Service {

    public static WebSocketClient client;
    private static WebsocketServerListener mTextChatManagerListener;
    public static int messageId = 0;

    public interface WebsocketServerListener {

        void onReceiveTextChatMessage(String message);
    }

    public static void setWebsocketServerListener(WebsocketServerListener listener) {
        mTextChatManagerListener = listener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connectServer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public static void connectServer() {
        if ((isWebSocketClientAvaliable()) || (!isNetConnect())) { return; }
        try {
            URI uri = new URI(BuildConfig.DEBUG ? ConstPath.TEXT_CHAT_DEFAULT_SERVICE : ConstPath.TEXT_CHAT_DEFAULT_SERVICE);
            client = new WebSocketClient(uri, new Draft_6455(), null, 5000) {
                @Override
                public void onOpen(ServerHandshake arg0) {
                    LogUtils.d("TextChatManagerService  connectServer onOpen ");
                    ToastUtils.showLong("链接服务器成功");
                }

                @Override
                public void onMessage(String receiveMessage) {
                    LogUtils.d("TextChatManagerService connectServer Socket Receiver message :" + receiveMessage);
                    if (mTextChatManagerListener != null) {
                        mTextChatManagerListener.onReceiveTextChatMessage(receiveMessage);
                    }
                }

                @Override
                public void onError(Exception arg0) {
                    LogUtils.d("TextChatManagerService  connectServer onError ");
                }

                @Override
                public void onClose(int arg0, String arg1, boolean arg2) {
                    LogUtils.d("TextChatManagerService  connectServer onClose ");
                }
            };
            client.connect();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static boolean isWebSocketClientAvaliable() {
        return ((client != null) && (client.isOpen()) && (!client.isClosed()) && (!client.isClosing()) && (!client.isConnecting()));
    }

    public static void starWebSocketService() {
        LogUtils.d("TextChatManagerService  starWebSocketService()");
        try {
            Intent startIntent = new Intent(BaseApplication.getInstance(), CustomWebsocketServer.class);
            BaseApplication.getInstance().startService(startIntent);
        } catch (Exception e) {

        }
    }

    public static boolean isNetConnect() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
        return false;
    }


}
