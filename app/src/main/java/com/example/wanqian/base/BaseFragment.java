package com.example.wanqian.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.example.wanqian.R;
import com.example.wanqian.utitls.Logger;
import com.example.wanqian.widget.LoadingPage;

/*
 created by zhangyu on 2019-11-1
 **/
public abstract class BaseFragment extends RxFragment {

    private BaseActivity mBaseActivity;
    private String TAG;
    private LoadingPage mFragmentLoadingPage;
    public BaseFragment(){
        TAG = getClass().getSimpleName();
    }

    public int enter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_right_in;
    }

    public int exit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_out;
    }

    public int popEnter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_in;
    }

    public int popExit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_right_out;
    }


    public boolean isNeedAnimation() {
        return true;
    }

    protected boolean isNeedToAddBackStack() {
        return true;
    }


    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutId(), container, false);

        if(v instanceof FrameLayout || !isNeedShowLoadingPage()){
            return v;
        }

        FrameLayout frameLayout = new FrameLayout(getContext());

        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        frameLayout.addView(v);



        return frameLayout;
    }

    abstract protected  int getLayoutId();



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Logger.d("%s", TAG);
        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;
        }
    }


    protected BaseFragment addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {
        if (mBaseActivity != null) {
           return  mBaseActivity.addFragment(manager, aClass, containerId, args);
        }
        return null;
    }




    protected void showToast(String msg){
        mBaseActivity.showToast(msg);
    }

    protected void showToast(@StringRes int resId){
        mBaseActivity.showToast(resId);
    }



    protected void onError(String msg, LoadingPage.OnReloadListener listener) {
        if (mFragmentLoadingPage != null && mFragmentLoadingPage.getParent() != null) {
            mFragmentLoadingPage.onError(msg, listener);
        }
    }

    protected void showLoadingPage(int mode) {

        if(mode == LoadingPage.MODE_1){
            showLoadingPage(mode, android.R.id.content);
        }else{
            showLoadingPage(mode, (ViewGroup) getView());
        }

    }

    protected void showLoadingPage(int mode, @IdRes int groupId) {
        showLoadingPage(mode, (ViewGroup) getView().findViewById(groupId));
    }

    protected void showLoadingPage(int mode, ViewGroup group) {
        if (mFragmentLoadingPage == null) {
            mFragmentLoadingPage = (LoadingPage) LayoutInflater.from(getContext()).inflate(R.layout.layout_loading, null);
        }
        if (mFragmentLoadingPage.getParent() == null) {
            group.addView(mFragmentLoadingPage);
        }
        mFragmentLoadingPage.show(mode);
    }


    protected void dismissLoadingPage() {
        if (mFragmentLoadingPage != null) {
            mFragmentLoadingPage.dismiss();
        }
    }
    protected String getLogTag(){
        return TAG;
    }

    protected boolean isNeedShowLoadingPage(){
        return true;
    }
}
