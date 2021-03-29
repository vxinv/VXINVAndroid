package com.example.wanqian.ui.locationMessage;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wanqian.R;
import com.example.wanqian.bean.AllMessageDeviceBean;
import com.example.wanqian.bean.TabEntity;
import com.example.wanqian.widget.dialogFragment.widget.BaseDialog;
import com.example.wanqian.widget.dialogFragment.widget.ViewHolder;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocationDialogFragment extends BaseDialog {

    private List<AllMessageDeviceBean> deviceBeanList;
    private CommonTabLayout tabLayout;
    private ViewPager main_viewpager;

    public static LocationDialogFragment newInstance(List<AllMessageDeviceBean>  list) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) list);
        LocationDialogFragment dialog = new LocationDialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        deviceBeanList = (List<AllMessageDeviceBean>) bundle.getSerializable("list");
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.pop_locationpop;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseDialog dialog) {
        tabLayout=holder.getView(R.id.tab);
        main_viewpager=holder.getView(R.id.viewPager);
        initView();

    }

    private void initView() {

        List<Fragment> tabContents=new ArrayList<>();
        for (int i=0;i<deviceBeanList.size();i++){
            LocationPopFragment locationFragment=new LocationPopFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable("data",deviceBeanList.get(i));
            locationFragment.setArguments(bundle);
            tabContents.add(locationFragment);
        }
        LocationPopAdapter mAdapter = new LocationPopAdapter(getChildFragmentManager());
        mAdapter.setData(tabContents);
        main_viewpager.setAdapter(mAdapter);
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();

        for (int i=0;i<deviceBeanList.size();i++){
            tabEntities.add(new TabEntity(deviceBeanList.get(i).getDevName()));
        }
        if (deviceBeanList.size()==1){
            tabLayout.setVisibility(View.GONE);
        }
        tabLayout.setTabData(tabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                main_viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        main_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                tabLayout.setCurrentTab(arg0);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }



}
