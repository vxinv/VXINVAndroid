package com.example.wanqian.newbase;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.newbase.mvp.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class NewBaseFragment<P extends BasePresenter> extends Fragment implements BaseView, View.OnClickListener {

    public View view;
    public Activity mContext;
    protected P mPresenter;
    private Unbinder unbinder;

    protected abstract P createPresenter();

    private static final String TAG = "NewBaseFragment";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(getLayoutId(), null);
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 在 onCreateView 之后调用
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lazyLoaded();
    }

    private void lazyLoaded() {
        initData();
        initListener();
    }

    protected abstract void initListener();

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();


    /**
     * 数据初始化操作
     */
    protected abstract void initData();



    @Override
    public void showError(String msg) {

    }

    @Override
    public void onErrorCode(BaseModel model) {
    }

    @Override
    public void netWorkConnect(boolean connect) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.view = null;
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View view) {
        otherViewClick(view);
    }

    protected abstract void otherViewClick(View view);

}
