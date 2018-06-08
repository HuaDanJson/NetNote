package cool.monkey.android.websocketclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cool.monkey.android.websocketclient.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welecome);
        //第一：默认初始化
        Bmob.initialize(this, "9b6612ae654764c1774e625e3acb6bfe");
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            // 允许用户使用应用
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        }
    }
}
