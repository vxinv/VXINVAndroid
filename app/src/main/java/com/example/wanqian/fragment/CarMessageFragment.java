package com.example.wanqian.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanqian.R;
import com.example.wanqian.bean.CarMessageBaseBean;
import com.example.wanqian.newbase.NewBaseFragment;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.presenter.CarMessagePresenter;
import com.example.wanqian.ui.carMessage.CarMessageAdapter;
import com.example.wanqian.utitls.SPUtils;
import com.example.wanqian.utitls.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

@SuppressWarnings("MagicConstant")
public class CarMessageFragment extends NewBaseFragment<CarMessagePresenter> implements BaseBckDataView {

    @BindView(R.id.ry_work)
    RecyclerView mRcy;
    @BindView(R.id.sf_fresh)
    SmartRefreshLayout mRefreshLayout;
    private CarMessageBaseBean data;
    private int pageNum=1;
    private CarMessageAdapter adapter;
    @Override
    protected CarMessagePresenter createPresenter() {
        return new CarMessagePresenter(this);
    }
    @Override
    protected void initListener() {

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_carmessage;
    }

    @Override
    protected void initData() {
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.autoRefresh();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRcy.setLayoutManager(layoutManager);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum=1;
                mPresenter.getCarMessage(SPUtils.getValue("yhm"),pageNum+"","20");
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
                mPresenter.getCarMessage(SPUtils.getValue("yhm"),pageNum+"","20");
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
        adapter=new CarMessageAdapter(mContext);
        mRcy.setAdapter(adapter);
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @Override
    public void getBaseData(BaseModel baseModel) {
        data= (CarMessageBaseBean) baseModel.getData();
        if (data != null) {
            if (data.getList()==null||data.getList().size()<20){
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }else{
                mRefreshLayout.finishLoadMore();
            }
            if (pageNum==1){
                adapter.setData(data.getList());
            }else{
                if (data.getList()!=null&data.getList().size()!=0){
                    adapter.addData(data.getList());
                    adapter.notifyDataSetChanged();
                }
            }

        }
        mRefreshLayout.finishRefresh(true);
    }

    @Override
    public void getErrorMsg(String msg) {
        mRefreshLayout.finishRefresh(true);
        ToastUtils.showSingleToast(msg);
    }
}
