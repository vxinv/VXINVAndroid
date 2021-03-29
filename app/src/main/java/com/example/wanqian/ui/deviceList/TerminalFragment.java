package com.example.wanqian.ui.deviceList;


import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wanqian.R;
import com.example.wanqian.bean.AllMessageDeviceBean;
import com.example.wanqian.bean.TerminalNewBean;
import com.example.wanqian.newbase.NewBaseFragment;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.presenter.TerminalPresenter;
import com.example.wanqian.ui.deviceList.adapter.DeviceListAdapter;
import com.example.wanqian.ui.locationMessage.LocationDialogFragment;
import com.example.wanqian.utitls.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class TerminalFragment extends NewBaseFragment<TerminalPresenter> implements BaseBckDataView{

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.terminal_Ry)
    RecyclerView ry;
    private int page=1;
    private  DeviceListAdapter adapter;

    @Override
    protected TerminalPresenter createPresenter() {
        return new TerminalPresenter(this);
    }

    @Override
    protected void initListener() {
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.autoRefresh();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        ry.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page=1;
            mPresenter.getTerminalList(page);
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            mPresenter.getTerminalList(page);
        });
        adapter=new DeviceListAdapter(mContext);
        adapter.setOnchangeDataClickListener(bean -> {
            List<String> data=new ArrayList<>();
            data.add(bean.getDevId());
            mPresenter.SelectTerminal(data);
        });
        ry.setAdapter(adapter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_terminal;
    }

    @Override
    protected void initData() {
        mPresenter.getTerminalList(page);
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @Override
    public void getBaseData(BaseModel baseModel) {
       if (baseModel.getData() instanceof TerminalNewBean){
           TerminalNewBean dataBean= (TerminalNewBean) baseModel.getData();
           if (page==1){
               adapter.setData(dataBean.getList());
           }else{
               if (adapter!=null){
                   adapter.addData(dataBean.getList());
               }
           }
           refreshLayout.finishRefresh(true);
           if (dataBean.getList()==null||dataBean.getList().size()<20){
               refreshLayout.finishLoadMoreWithNoMoreData();
           }else{
               refreshLayout.finishLoadMore();
           }
       }else{
           if (baseModel.getData()!=null){
               List<AllMessageDeviceBean> data= (List<AllMessageDeviceBean>) baseModel.getData();
               LocationDialogFragment.newInstance(data).setOutCancel(true).show(getChildFragmentManager());
           }
       }
    }

    @Override
    public void getErrorMsg(String msg) {
        refreshLayout.finishRefresh(true);
        refreshLayout.finishLoadMore();
        ToastUtils.showSingleToast(msg);
    }
}
