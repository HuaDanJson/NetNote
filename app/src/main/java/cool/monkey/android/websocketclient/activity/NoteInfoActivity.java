package cool.monkey.android.websocketclient.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cool.monkey.android.websocketclient.R;
import cool.monkey.android.websocketclient.bean.DBUserInvestment;
import cool.monkey.android.websocketclient.bean.DataSaveEvent;
import cool.monkey.android.websocketclient.constants.ConstKey;
import cool.monkey.android.websocketclient.utils.DBUserInvestmentUtils;
import cool.monkey.android.websocketclient.utils.RxBus;
import cool.monkey.android.websocketclient.view.NoteEditText;

public class NoteInfoActivity extends AppCompatActivity {

    @BindView(R.id.tvAddActivityTitle)
    TextView tvAddActivityTitle;
    @BindView(R.id.edtNotXiangQingTitle)
    EditText edtNotXiangQingTitle;
    @BindView(R.id.edtNotXiangQingTime)
    EditText edtNotXiangQingTime;
    @BindView(R.id.edtNotXiangQingCount)
    NoteEditText edtNotXiangQingCount;
    @BindView(R.id.btnNotXiangQingActivityUpdata)
    Button btnNotXiangQingActivityUpdata;
    @BindView(R.id.btnNotXiangQingActivityDelete)
    Button btnNotXiangQingActivityDelete;
    @BindView(R.id.btnNotXiangQingActivityCacel)
    Button btnNotXiangQingActivityCacel;
    @BindView(R.id.rlNotXiangQingActivity)
    RelativeLayout rlNotXiangQingActivity;
    private DBUserInvestment dbUserInvestment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);
        ButterKnife.bind(this);
        initData();
    }

    public void initData() {
        dbUserInvestment = DBUserInvestmentUtils.getInstance().queryOneData(getIntent().getLongExtra("NotID", 0));
        edtNotXiangQingTitle.setText(dbUserInvestment.getInvestmentCount());
        SimpleDateFormat sdr1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String CreatedTime1 = sdr1.format(new Date(dbUserInvestment.getCreatTimeAsId()));
        edtNotXiangQingTime.setText(CreatedTime1);
        edtNotXiangQingCount.setText(dbUserInvestment.getSign());
    }

    //设置点击事件
    @OnClick({R.id.btnNotXiangQingActivityUpdata, R.id.btnNotXiangQingActivityDelete, R.id.btnNotXiangQingActivityCacel})
    public void onClick(View view) {
        switch (view.getId()) {
            //处理点击更新点击事件
            case R.id.btnNotXiangQingActivityUpdata:
                saveNote();
                RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
                NoteInfoActivity.this.finish();
                break;
            //处理点击删除点击事件
            case R.id.btnNotXiangQingActivityDelete:
                DBUserInvestmentUtils.getInstance().deleteOneData(dbUserInvestment);
                RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
                NoteInfoActivity.this.finish();
                break;
            case R.id.btnNotXiangQingActivityCacel:
                NoteInfoActivity.this.finish();
                break;
            default:
                break;
        }
    }

    //保存备忘录
    public void saveNote() {
        //取得输入的内容
        String title = edtNotXiangQingTitle.getText().toString().trim();
        String content = edtNotXiangQingCount.getText().toString().trim();
        //内容和标题都不能为空
        if ("".equals(title) || "".equals(content)) {
            Toast.makeText(NoteInfoActivity.this, "名称和内容都不能为空", Toast.LENGTH_SHORT).show();
        } else {
            dbUserInvestment.setInvestmentCount(title);
            dbUserInvestment.setSign(content);
            DBUserInvestmentUtils.getInstance().updateData(dbUserInvestment);
            Toast.makeText(NoteInfoActivity.this, "更新数据成功", Toast.LENGTH_SHORT).show();
            RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
        }
    }
}
