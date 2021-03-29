package com.example.wanqian.ui;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.wanqian.R;
import com.example.wanqian.newbase.NewBaseActivity;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.presenter.UpdatePsPresenter;
import com.example.wanqian.utitls.SPUtils;
import com.example.wanqian.utitls.ToastUtils;
import com.example.wanqian.utitls.Utils;

import butterknife.BindView;

public class UpdatePasswordActivity extends NewBaseActivity<UpdatePsPresenter> implements BaseBckDataView {

    @BindView(R.id.original_et)
    EditText original_et;
    @BindView(R.id.newPs_et)
    EditText newPs_et;
    @BindView(R.id.sure_NewPs_et)
    EditText sure_NewPs_et;
    @BindView(R.id.toolbar_iv)
    LinearLayout toolbar_iv;
    @BindView(R.id.tv_update)
    TextView tv_update;
    @BindView(R.id.original_bt)
    ToggleButton original_bt;
    @BindView(R.id.original_new_bt)
    ToggleButton original_new_bt;
    @BindView(R.id.original_new_sure)
    ToggleButton original_new_sure;



    @Override
    protected UpdatePsPresenter createPresenter() {
        return new UpdatePsPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xiu_gai;
    }

    @Override
    public void configViews() {

    }

    @Override
    public void initDatas() {
        tv_update.setOnClickListener(this::otherViewClick);
        toolbar_iv.setOnClickListener(this::otherViewClick);
        original_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    original_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    original_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        original_new_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    newPs_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    newPs_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        original_new_sure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    sure_NewPs_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    sure_NewPs_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.tv_update:
                if (checking()){
                    mPresenter.UpdatePs(sure_NewPs_et.getText().toString());
                }
                break;
            case R.id.toolbar_iv:
                finish();
                break;

        }
    }

    public boolean checking(){
        if (Utils.isBlank(original_et.getText().toString().trim())){
            ToastUtils.showSingleToast("原密码不可以为空");
            return false;
        }
        if (!original_et.getText().toString().trim().equals(SPUtils.getValue("psw"))){
            ToastUtils.showSingleToast("请检查原密码是否正确");
            return false;
        }
        if (Utils.isBlank(newPs_et.getText().toString().trim())){
            ToastUtils.showSingleToast("新密码不可以为空");
            return false;
        }

        if (newPs_et.getText().toString().trim().length()<6){
            ToastUtils.showSingleToast("新密码长度必须大于6个字符");
            return false;
        }
        if (Utils.isBlank(sure_NewPs_et.getText().toString().trim())){
            ToastUtils.showSingleToast("请再次输入密码");
            return false;
        }
        if (!sure_NewPs_et.getText().toString().trim().equals(newPs_et.getText().toString().trim())){
            ToastUtils.showSingleToast("两次密码输入不同,请检查");
            return false;
        }
        return true;
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
         SPUtils.saveValueToDefaultSpByApply("mm", newPs_et.getText().toString().trim());
         ToastUtils.showSingleToast(baseModel.getMsg());
         finish();
    }

    @Override
    public void getErrorMsg(String msg) {
        ToastUtils.showSingleToast(msg);
    }
}
