package cool.monkey.android.websocketclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cool.monkey.android.websocketclient.R;
import cool.monkey.android.websocketclient.adapter.MyKindAdapter;
import cool.monkey.android.websocketclient.bean.DBUserInvestment;
import cool.monkey.android.websocketclient.bean.DataSaveEvent;
import cool.monkey.android.websocketclient.constants.ConstKey;
import cool.monkey.android.websocketclient.utils.DBUserInvestmentUtils;
import cool.monkey.android.websocketclient.utils.RxBus;
import rx.Subscription;
import rx.functions.Action1;

public class NoteActivity extends AppCompatActivity {

    @BindView(R.id.btnKnowledgeActivityAdd)
    Button btnKnowledgeActivityAdd;
    @BindView(R.id.lvKnowledgeActivity)
    ListView lvKnowledgeActivity;
    private Subscription rxSubscription;
    private List<DBUserInvestment> resultDaoList = new ArrayList<>();
    private MyKindAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);
        refreshMediaUpdateEvent();
        getListViewData();
        setListViewClick();
    }

    public void getListViewData() {
        resultDaoList = DBUserInvestmentUtils.getInstance().queryALLData();
        if (resultDaoList != null && resultDaoList.size() > 0) {
            myAdapter = new MyKindAdapter(resultDaoList, NoteActivity.this);
            lvKnowledgeActivity.setAdapter(myAdapter);
        }
    }

    public void setListViewClick() {
        lvKnowledgeActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteActivity.this, NoteInfoActivity.class);
                intent.putExtra("NotID", resultDaoList.get(position).getCreatTimeAsId());
                startActivity(intent);
            }
        });
    }

    //设置点击事件
    @OnClick({R.id.btnKnowledgeActivityAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            //处理点击添加点击事件
            case R.id.btnKnowledgeActivityAdd:
                Intent intent = new Intent(NoteActivity.this, AddNoteActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void refreshMediaUpdateEvent() {
        rxSubscription = RxBus.getDefault()
                .toObservable(DataSaveEvent.class)
                .subscribe(new Action1<DataSaveEvent>() {
                    @Override
                    public void call(DataSaveEvent dataSaveEvent) {
                        if (ConstKey.SAVE_DATA_SUCCESS.equals(dataSaveEvent.getDataSaveEvent())) {
                            getListViewData();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterRxBus();
    }

    private void unregisterRxBus() {
        if (rxSubscription != null && !rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }
}
