package com.example.wanqian.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wanqian.R;
import com.example.wanqian.utitls.Logger;
import com.example.wanqian.widget.LoadingPage;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/*
 * created by zhangyu on 2019-11-1
 **/
public class BaseActivity extends RxAppCompatActivity {

    private LoadingPage mLoadingPage;
    private String TAG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = getClass().getSimpleName();
    }

    protected BaseFragment addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {

        String tag = aClass.getName();

        Logger.d("%s add fragment %s", TAG, aClass.getSimpleName());

        /**
         * 先查询fragmentManager 所在的activitiy 中是否已经添加了这个fragment
         *  第一步 先从一个mAdded 的一个ArrayList遍历查找，如果找不到再从 一个 叫 mActive 的 SparseArray的一个map
         *  里面查找。
         *
         *
         * 注意：
         * 1.一个 fragment 被 remove 掉后，只会从 mAdded 里面删除，不会从 mActive 里面删除，只有当这个fragment 所在的 transaction
         * 从回退栈里面移除后才会 从mActive 删除
         * 2. 当我们add 一个fragment时 会把我们的fragment 添加到 mAdded 里面，不会添加到 mActive。
         * 3. 只有当我们把 transaction 添加到回退栈的时候，才会把我们的 fragment 添加到 mActive 里面。
         *
         *
         * 所以我们通过 findFragmentByTag 方法查找出来的 fragment 不一定是被添加到我们的 activity 中。
         */

        Fragment fragment = manager.findFragmentByTag(tag);


        FragmentTransaction transaction = manager.beginTransaction(); // 开启一个事务

        if (fragment == null) {// 没有添加
            try {
                fragment = aClass.newInstance(); // 通过反射 new 出一个 fragment 的实例

                BaseFragment baseFragment = (BaseFragment) fragment; // 强转成我们base fragment

                // 设置 fragment 进入，退出， 弹进，弹出的动画
                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());

                transaction.add(containerId, fragment, tag);

                if (baseFragment.isNeedToAddBackStack()) { // 判断是否需要加入回退栈
                    transaction.addToBackStack(tag); // 加入回退栈时制定一个tag，以便在找到指定的事务
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
            } else {
                transaction.add(containerId, fragment, tag);
            }
        }

        if (fragment != null) {
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();
            return (BaseFragment) fragment;

        }

        return null;
    }


    /**
     * 除当前 fragment 以外的所有 fragment 进行隐藏
     *
     * @param manager
     * @param transaction
     * @param currentFragment
     */
    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {

        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }


    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int rId) {
        showToast(getString(rId));
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    protected void onError(String msg, LoadingPage.OnReloadListener listener) {
        if (mLoadingPage != null && mLoadingPage.getParent() != null) {
            mLoadingPage.onError(msg, listener);
        }
    }

    protected void showLoadingPage(int mode) {
        showLoadingPage(mode, android.R.id.content);
    }

    protected void showLoadingPage(int mode, @IdRes int groupId) {
        showLoadingPage(mode, (ViewGroup) findViewById(groupId));
    }

    protected void showLoadingPage(int mode, ViewGroup group) {
        if (mLoadingPage == null) {
            mLoadingPage = (LoadingPage) LayoutInflater.from(this).inflate(R.layout.layout_loading, null);
        }
        if (mLoadingPage.getParent() == null) {
            group.addView(mLoadingPage);
        }
        mLoadingPage.show(mode);
    }


    protected void dismissLoadingPage() {
        if (mLoadingPage != null) {
            mLoadingPage.dismiss();
        }
    }


}
