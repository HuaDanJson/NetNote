package cool.monkey.android.websocketclient.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cool.monkey.android.websocketclient.R;
import cool.monkey.android.websocketclient.bean.DBUserInvestment;
import cool.monkey.android.websocketclient.bean.DataSaveEvent;
import cool.monkey.android.websocketclient.constants.ConstKey;
import cool.monkey.android.websocketclient.utils.DBUserInvestmentUtils;
import cool.monkey.android.websocketclient.utils.RxBus;
import cool.monkey.android.websocketclient.view.NoteEditText;

public class AddNoteActivity extends AppCompatActivity {
    @BindView(R.id.tvAddActivityTitle)
    TextView tvAddActivityTitle;
    //标题、内容和时间
    private EditText editText_add_title, editText_add_time;
    private NoteEditText noteEditText_add_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        editText_add_title = (EditText) findViewById(R.id.editText_add_title);
        editText_add_time = (EditText) findViewById(R.id.editText_add_time);
        noteEditText_add_content = (NoteEditText) findViewById(R.id.noteEditText_add_content);
        editText_add_time.setText(formatTime());
    }

    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.button_add_save:
                //保存备忘录信息
                saveNote();
                AddNoteActivity.this.finish();
                break;
            case R.id.button_add_cacel:
                AddNoteActivity.this.finish();
                break;
        }
    }

    //保存备忘录
    public void saveNote() {
        //取得输入的内容
        String title = editText_add_title.getText().toString().trim();
        String content = noteEditText_add_content.getText().toString().trim();
        String time = editText_add_time.getText().toString().trim();
        //内容和标题都不能为空
        if ("".equals(title) || "".equals(content)) {
            Toast.makeText(AddNoteActivity.this, "名称和内容都不能为空", Toast.LENGTH_SHORT).show();
        } else {
            DBUserInvestment dbUserInvestment = new DBUserInvestment();
            dbUserInvestment.setCreatTimeAsId(getTime());
            dbUserInvestment.setInvestmentCount(title);
            dbUserInvestment.setSign(content);
            DBUserInvestmentUtils.getInstance().insertOneData(dbUserInvestment);
            Toast.makeText(AddNoteActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
        }

    }

    //返回当前的时间
    public String formatTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(d);
        return time;
    }

    public long getTime() {
        return System.currentTimeMillis();//获取系统时间戳
    }

}
