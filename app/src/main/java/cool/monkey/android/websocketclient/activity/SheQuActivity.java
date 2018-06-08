package cool.monkey.android.websocketclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cool.monkey.android.websocketclient.R;
import cool.monkey.android.websocketclient.adapter.NoteAdapter;
import cool.monkey.android.websocketclient.bean.Note;
import cool.monkey.android.websocketclient.utils.ToastHelper;

public class SheQuActivity extends AppCompatActivity {

    @BindView(R.id.tv_title_note_activity) TextView mTitle;
    @BindView(R.id.tv_send_note_activity) TextView mSendNote;
    @BindView(R.id.rv_note_activity) RecyclerView mRecyclerView;

    List<Note> noteList = new ArrayList<>();
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_qu);
        ButterKnife.bind(this);
        getMediaData();
    }

    public void getMediaData() {
        BmobQuery<Note> query = new BmobQuery<Note>();
        // 按时间降序查询
        query.order("-createdAt");
        query.setLimit(20);
        //从服务器获取衣服图片 集合 Arraylist
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e == null) {
                    noteList = list;
                    if (noteList.size() == 0) {
                        ToastHelper.showShortMessage("还未有编写的帖子");
                    } else {
                        if (mRecyclerView != null) {
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(SheQuActivity.this));
                            noteAdapter = new NoteAdapter(noteList, SheQuActivity.this);
                            mRecyclerView.setAdapter(noteAdapter);
                        }
                    }
                } else {
                    ToastHelper.showShortMessage("获取数据失败");
                }

            }
        });
    }

    @OnClick(R.id.tv_send_note_activity)
    public void senNote() {
        Intent intent = new Intent(SheQuActivity.this, SendNoteActivity.class);
        startActivityForResult(intent, 999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            getMediaData();
        }
    }
}
