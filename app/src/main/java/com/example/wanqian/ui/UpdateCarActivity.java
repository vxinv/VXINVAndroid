/*package com.example.wanqian.ui;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.example.wanqian.R;
import com.example.wanqian.bean.BindCarMsBean;
import com.example.wanqian.newbase.NewBaseActivity;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.pView.UpdateCarView;
import com.example.wanqian.presenter.UpdateCarPresenter;
import com.example.wanqian.utitls.SPUtils;
import com.example.wanqian.utitls.ToastUtils;
import com.example.wanqian.utitls.Utils;
import butterknife.BindView;

public class UpdateCarActivity extends NewBaseActivity<UpdateCarPresenter> implements UpdateCarView {

    @BindView(R.id.toolbar_iv)
    LinearLayout toolbar_iv;
    @BindView(R.id.car_number_et)
    EditText car_number;
    @BindView(R.id.tv_commit)
    TextView tv_commit;

    @Override
    protected UpdateCarPresenter createPresenter() {
        return new UpdateCarPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_car;
    }

    @Override
    public void configViews() {
         toolbar_iv.setOnClickListener(this::otherViewClick);
         tv_commit.setOnClickListener(this::otherViewClick);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initDatas() {
        if (TextUtils.isEmpty(SPUtils.getValue("CarNumberMs"))){
            tv_commit.setText("绑定");
            mPresenter.getBindCar(Utils.getIMei(mContext));
        }else{
            car_number.setEnabled(false);
            car_number.setClickable(false);
            car_number.setText(SPUtils.getValue("CarNumberMs")+"");
            tv_commit.setText("已绑定");
            tv_commit.setEnabled(false);
            tv_commit.setClickable(false);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.toolbar_iv:
                finish();
                break;
            case R.id.tv_commit:
                if (TextUtils.isEmpty(car_number.getText().toString().trim())){
                    ToastUtils.showSingleToast("请输入车牌号码");
                }else{
                    mPresenter.upDa(car_number.getText().toString().trim(), Utils.getIMei(mContext));
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
         SPUtils.saveValueToDefaultSpByApply("CarNumberMs", car_number.getText().toString().trim());
         ToastUtils.showSingleToast(baseModel.getMsg());
         finish();
    }

    @Override
    public void getCarMs(BaseModel baseModel) {
        Log.d("-----------------",baseModel.toString());
        if (baseModel.getData()!=null&&baseModel.getData() instanceof BindCarMsBean){
            BindCarMsBean bean= (BindCarMsBean) baseModel.getData();
            if (TextUtils.isEmpty(bean.getCarNumber())){
                car_number.setEnabled(false);
                car_number.setClickable(false);
                car_number.setText(SPUtils.getValue("CarNumberMs")+"");
                tv_commit.setText("已绑定");
                tv_commit.setEnabled(false);
                tv_commit.setClickable(false);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }

        }
    }


    @Override
    public void getErrorMsg(String msg) {
        ToastUtils.showSingleToast(msg);
    }


}*/
