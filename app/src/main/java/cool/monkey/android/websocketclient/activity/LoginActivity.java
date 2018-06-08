package cool.monkey.android.websocketclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cool.monkey.android.websocketclient.R;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.editLoginActivityUsrName)
    EditText editLoginActivityUsrName;
    @BindView(R.id.editLoginActivityPassWord)
    EditText editLoginActivityPassWord;
    @BindView(R.id.btnLoginActivityLogin)
    AppCompatButton btnLoginActivityLogin;
    @BindView(R.id.tvLoginActivityRegister)
    TextView tvLoginActivityRegister;
    @BindView(R.id.llLoginActivityAll)
    LinearLayout llLoginActivityAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Bmob.initialize(this, "9b6612ae654764c1774e625e3acb6bfe");
        getGetWritePermission();
        btnLoginActivityLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoginOnclick();
            }
        });

        tvLoginActivityRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getGetWritePermission() {

        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        shouldRequest.again(true);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {

                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            PermissionUtils.launchAppDetailsSettings();
                        }
                    }
                }).request();
    }

    public void setLoginOnclick() {
        String username = editLoginActivityUsrName.getText().toString();
        String password = editLoginActivityPassWord.getText().toString();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            BmobUser userInfoBean = new BmobUser();
            userInfoBean.setUsername(username);
            userInfoBean.setPassword(password);
            userInfoBean.login(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser userInfoBean, BmobException e) {
                    if (e == null) {
                        BmobUser currentUser = BmobUser.getCurrentUser(BmobUser.class);
                        Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败 : " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
