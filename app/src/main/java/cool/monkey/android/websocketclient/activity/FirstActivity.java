package cool.monkey.android.websocketclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cool.monkey.android.websocketclient.R;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.btn_chat_activity) Button btnChatActivity;
    @BindView(R.id.btn_she_qu_activity) Button btnSheQuActivity;
    @BindView(R.id.btn_userInfo_activity) Button btnUserInfoActivity;
    @BindView(R.id.btn_log_out_activity) Button btnLogOutActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        Bmob.initialize(this, "9b1536fd1e7bd5615b5c4d0ea8cc92c8");
    }

    @OnClick(R.id.btn_chat_activity)
    public void chatClick() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick(R.id.btn_she_qu_activity)
    public void shequClick() {

        startActivity(new Intent(this, SheQuActivity.class));
    }

    @OnClick(R.id.btn_userInfo_activity)
    public void userinfoClick() {
        startActivity(new Intent(this, UserInfoActivity.class));
    }

    @OnClick(R.id.btn_log_out_activity)
    public void logoutClick() {
        startActivity(new Intent(this, LoginActivity.class));
        BmobUser.logOut();   //清除缓存用户对象
        BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
        finish();
    }
}
