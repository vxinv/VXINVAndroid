package com.example.wanqian.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wanqian.R;
import com.example.wanqian.bean.SplashBean;
import com.example.wanqian.newbase.NewBaseActivity;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.presenter.SplashPresenter;
import com.example.wanqian.utitls.SPUtils;
import com.example.wanqian.widget.CheckPremissionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SplashActivity extends NewBaseActivity<SplashPresenter> implements BaseBckDataView {

    private final int REQ_READ_CODE = 1009;
    private final int REQ_SETTING_CODE = 1019;
    @BindView(R.id.tv_jump)
    TextView tv_jump;
    @BindView(R.id.iw_pic)
    ImageView iw_pic;
    private List<String> mPermissionList = new ArrayList<>();
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE

    };
    private boolean dialogIsShow = false;
    private CheckPremissionDialog confirmDialog;
    private CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (SPUtils.getbooleanValue("userFlag")) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, NewLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            iw_pic.setImageBitmap(resource);
            countDownTimer.start();
        }
    };

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_spalsh;
    }

    @Override
    public void configViews() {

        tv_jump.setOnClickListener(this::otherViewClick);
    }

    public void initDatas() {
        checkPermission();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jump:
                countDownTimer.cancel();
                if (SPUtils.getbooleanValue("userFlag")) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, NewLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

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
    public void getBaseData(BaseModel baseModel) {

        if (baseModel != null) {
            SplashBean bean = (SplashBean) baseModel.getData();
            if (!TextUtils.isEmpty(bean.getUrl())) {
                Glide.with(mContext).load(bean.getUrl()).into(target);
            } else {
                countDownTimer.start();
            }
        }
    }

    @Override
    public void getErrorMsg(String msg) {
        countDownTimer.start();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //判断是否有写SD卡的权限，没有有则申请
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permission);
                }
            }
            if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
                if (dialogIsShow) {
                    ActivityCompat.requestPermissions(this, permissions, REQ_READ_CODE);
                } else {
                    showPermissionDialog(false);
                }
            } else {
                //说明权限都已经通过，可以做你想做的事情去
                //AppLog.d(TAG, "权限全部申请了1");
                mPresenter.getSplash();
            }
        } else {
            // AppLog.d(TAG, "当前版本不需要动态申请权限");
            mPresenter.getSplash();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        boolean hasPermissionDismiss = false;//有权限没有通过
        boolean chooseAndDismiss = true;//拒绝权限并勾选
        if (REQ_READ_CODE == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                //跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                //  AppLog.d(TAG, "不让他继续访问");
                for (int i = 0; i < permissions.length; i++) {
                    if (shouldShowRequestPermissionRationale(permissions[i])) {
                        chooseAndDismiss = false;
                    }
                }
                showPermissionDialog(chooseAndDismiss);
            } else {
                //全部权限通过，可以进行下一步操作。。。
                //  AppLog.d(TAG, "权限全部申请了2");

                mPresenter.getSplash();
            }
        }


    }


    private void showPermissionDialog(boolean goSettings) {

        confirmDialog = new CheckPremissionDialog(this, goSettings ? "前往开启" : "确定");
        confirmDialog.setOnConfirmClickListener(() -> {

            if (goSettings) {
                try {
                    Intent intent = new Intent()
                            .setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
                            .setData(Uri.fromParts("package", getApplicationContext().getPackageName(), null));
                    startActivityForResult(intent, REQ_SETTING_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                dialogIsShow = true;
                checkPermission();
            }
        });
        if (confirmDialog != null && !confirmDialog.isShowing()) {
            confirmDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SETTING_CODE) {

            checkPermission();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: Spalsh");
    }
}
