package cool.monkey.android.websocketclient.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cool.monkey.android.websocketclient.R;
import cool.monkey.android.websocketclient.bean.Note;
import cool.monkey.android.websocketclient.utils.ToastHelper;

public class SendNoteActivity extends AppCompatActivity {

    @BindView(R.id.edtFeedbackActivityEmail) EditText edtTitle;
    @BindView(R.id.edtFeedbackActivityFeedback) EditText edtValue;
    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.llFeedbackActivityCommit) LinearLayout llFeedbackActivityCommit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_note);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.llFeedbackActivityCommit)
    public void sendNote() {
        Note note = new Note();
        note.setNoteTitle(edtTitle.getText().toString());
        note.setNoteContent(edtValue.getText().toString());
        note.setCreatTime(System.currentTimeMillis());
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            note.setSendUserName(bmobUser.getUsername());
        }
        note.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastHelper.showShortMessage("发布成功");
                    finish();
                } else {
                    ToastHelper.showShortMessage("发布失败");
                }
            }
        });
    }
}
