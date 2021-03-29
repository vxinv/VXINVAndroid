package com.example.wanqian.ui;


import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.wanqian.R;
import com.example.wanqian.main.personage.login.bean.LoginBean;
import com.example.wanqian.newbase.NewBaseActivity;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.presenter.LoginPresenter;
import com.example.wanqian.utitls.SPUtils;
import com.example.wanqian.utitls.ToastUtils;
import java.util.Calendar;
import butterknife.BindView;

public class NewLoginActivity extends NewBaseActivity<LoginPresenter> implements BaseBckDataView {

    @BindView(R.id.yhm)
    EditText yhm;
    @BindView(R.id.mm)
    EditText mm;
    @BindView(R.id.denglu)
    TextView denglu;
    @BindView(R.id.togglePwd)
    ToggleButton togglePwd;
    @BindView(R.id.tv_com)
    TextView tv_com;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void configViews() {

        togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        denglu.setOnClickListener(this::otherViewClick);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);   //获取年份
        tv_com.setText("版权所有 ©" +year+ "万谦科技");
    }

    @Override
    public void initDatas() {
        if (SPUtils.getValue("yhm")!=null){
            yhm.setText(SPUtils.getValue("yhm"));
        }
        if (SPUtils.getValue("psw")!=null){
            mm.setText(SPUtils.getValue("psw"));
        }
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.denglu:
                if (submit()){
                    mPresenter.Login(yhm.getText().toString().trim(),mm.getText().toString().trim());
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
        if (baseModel.getData() instanceof LoginBean){
            LoginBean loginBean= (LoginBean) baseModel.getData();
            SPUtils.saveValueToDefaultSpByInt("id", loginBean.getId());
            SPUtils.saveValueToDefaultSpByApply("username", loginBean.getUserName());
            SPUtils.saveValueToDefaultSpByApply("yhm", yhm.getText().toString().trim());
            SPUtils.saveValueToDefaultSpByApply("psw",  mm.getText().toString().trim());
            SPUtils.saveValueToDefaultSpByBoolean("userFlag", true);
            Intent intent = new Intent(NewLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void getErrorMsg(String msg) {
         ToastUtils.showSingleToast(msg);
    }
    private boolean submit() {
        if (TextUtils.isEmpty(yhm.getText().toString().trim())) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty( mm.getText().toString().trim())) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
