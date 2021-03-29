package com.example.wanqian.newbase;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.newbase.mvp.BaseView;
import com.example.wanqian.utitls.AppLog;
import com.example.wanqian.utitls.BarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class NewBaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView,View.OnClickListener {

    protected Context mContext;
    protected String TAG;
    private Unbinder unbinder;
    protected P mPresenter;
    protected abstract P createPresenter();
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter = createPresenter();
        baseInitStutarbar();

        try {
            int layoutResID = getLayoutId();
            AppLog.d(layoutResID+"");
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                view=LayoutInflater.from(this).inflate(layoutResID,null);
                setContentView(layoutResID);
                unbinder= ButterKnife.bind(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        TAG = this.getClass().getSimpleName();
        mContext = this;
        initDatas();
        configViews();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
    public abstract int getLayoutId();
    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

    public abstract void initDatas();


    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected void baseInitStutarbar() {
        //透明顶部状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        BarUtils.setStatusBarLightMode(this, true);
    }

    @Override
    public void onClick(View view) {
        otherViewClick(view);
    }

    protected abstract void otherViewClick(View view);

}
