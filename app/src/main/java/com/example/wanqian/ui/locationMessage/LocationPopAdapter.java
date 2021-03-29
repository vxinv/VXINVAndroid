package com.example.wanqian.ui.locationMessage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class LocationPopAdapter extends FragmentPagerAdapter {

    private List<Fragment> mData;
    public LocationPopAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<Fragment> data) {
        mData = data;
        notifyDataSetChanged();
    }

}
