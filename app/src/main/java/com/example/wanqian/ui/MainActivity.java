package com.example.wanqian.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wanqian.BaseApp;
import com.example.wanqian.R;
import com.example.wanqian.fragment.LocationFragment;
import com.example.wanqian.fragment.MapFunctionFragment;
import com.example.wanqian.fragment.UpdateCarFragment;
import com.example.wanqian.main.zhu.adapter.VpAdapter;
import com.example.wanqian.main.zhu.tabother.BanViewPager;
import com.example.wanqian.ui.deviceList.TerminalFragment;
import com.example.wanqian.utitls.SPUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private TabLayout tab;
    private NavigationView nv;
    private DrawerLayout dl;
    private ImageView personage_side;
    private TextView mLeft_text1;
    private TextView mTuichu;
    private RelativeLayout personage_layout;
    private int currentItem;
    private ImageView center_job;
    private TextView textView;

    private static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* *
         * 屏幕常亮需要 申请屏幕 WAKE_LOCK 唤醒锁 权限
         *  <uses-permission android:name="android.permission.WAKE_LOCK" />
         * **/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initView() {
        textView=findViewById(R.id.text);
        center_job=findViewById(R.id.center_job);
        vp = (BanViewPager) findViewById(R.id.vp);
        tab =  findViewById(R.id.tab);
        nv = findViewById(R.id.nv);
        dl = findViewById(R.id.dl);
        personage_side =  findViewById(R.id.personage_side);
        personage_layout =  findViewById(R.id.personage_layout);
        //个人中心  页面图标的监听事件，也就是点击图标让其弹出侧滑菜单
        personage_layout.setOnClickListener(v -> {
            currentItem = vp.getCurrentItem();
            tab.setSelected(false);
            dl.openDrawer(Gravity.RIGHT);
        });
        //添加fragment
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new TerminalFragment());
        list.add(new LocationFragment());
        list.add(new MapFunctionFragment());
        list.add(new UpdateCarFragment());
        VpAdapter MyAdapter = new VpAdapter(getSupportFragmentManager(), list);
        vp.setAdapter(MyAdapter);
        vp.setCurrentItem(2);
        tab.setupWithViewPager(vp);
        tab.getTabAt(0).setIcon(R.drawable.lajitong_select).setText("设备列表");
        tab.getTabAt(1).setIcon(R.drawable.dingwei_select).setText("地图监控");
        tab.getTabAt(2).setIcon(null).setText("  ");
        tab.getTabAt(3).setIcon(R.drawable.car_select).setText("车辆绑定");
        vp.setOffscreenPageLimit(4);
        //判断侧滑菜单状态的监听事件
        dl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                /**
                 * 抽屉滑动时，调用此方法
                 * */
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                /**
                 * 抽屉被完全展开时，调用此方法
                 * */
                currentItem = vp.getCurrentItem();
                personage_side.setImageDrawable(getResources().getDrawable(R.mipmap.icon_geren_yes));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                /**
                 * 抽屉被完全关闭时，调用此方法
                 **/
                personage_side.setImageDrawable(getResources().getDrawable(R.mipmap.icon_geren_no));
                tab.getTabAt(currentItem).select();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                /**
                 * 抽屉状态改变时，调用此方法
                 * */
            }
        });
        setDrawer();
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    if (position==2){
                        center_job.setImageResource(R.mipmap.circlrs_bg_choose);
                    }else{
                        center_job.setImageResource(R.mipmap.circlrs_bg);
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置侧滑页
     */
    private void setDrawer() {
        View headerView = nv.getHeaderView(0);//获取头布局
        ImageView icon_jiantou = headerView.findViewById(R.id.icon_jiantou);
        RelativeLayout right_icon2 = headerView.findViewById(R.id.right_icon2);
//        RelativeLayout right_icon3 = headerView.findViewById(R.id.right_icon3);
        RelativeLayout right_icon4 = headerView.findViewById(R.id.right_icon4);
        RelativeLayout right_icon5 = headerView.findViewById(R.id.right_icon5);
        mLeft_text1 = headerView.findViewById(R.id.left_text1);
        mTuichu = headerView.findViewById(R.id.tuichu);
        String string = mLeft_text1.getText().toString();
        if ("未登录".equals(string)) {
            String username = SPUtils.getValue("username");
            mLeft_text1.setText("您好，" + username);
            mTuichu.setVisibility(View.VISIBLE);
            icon_jiantou.setVisibility(View.GONE);
        } else {
            mTuichu.setVisibility(View.GONE);
            icon_jiantou.setVisibility(View.VISIBLE);
        }
        mTuichu.setOnClickListener(v -> {
            SPUtils.saveValueToDefaultSpByBoolean("userFlag", false);
            Intent intent = new Intent(MainActivity.this, NewLoginActivity.class);
            startActivity(intent);
            finish();
        });
        right_icon2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UpdatePasswordActivity.class);
            startActivity(intent);
        });
//        right_icon3.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, UpdateCarActivity.class);
//            startActivity(intent);
//        });
        right_icon4.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewFeedBackActivity.class);
            startActivity(intent);
        });
        right_icon5.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy() {
        //unbindService(conn);//将service与activity解绑
        Log.i(TAG, "onDestroy MainActivity: =======================>");
        super.onDestroy();
        finish();
        AppExit(this);
    }


    public void AppExit(Context context) {
        try {
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception ignored) {
        }
    }

    public BaseApp getApp() {
        return ((BaseApp) getApplicationContext());
    }
}
