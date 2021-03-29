package com.example.wanqian.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wanqian.R;
import com.example.wanqian.utitls.SPUtils;
import com.example.wanqian.utitls.ToastUtils;

import butterknife.BindView;

public class UpdateCarFragment extends Fragment implements View.OnClickListener {

    /*  @BindView(R.id.toolbar_iv)
      LinearLayout toolbar_iv;*/
    @BindView(R.id.car_number_et)
    EditText car_number;
    @BindView(R.id.tv_commit_my)
    TextView tv_commit;
    TextView unlock;


    public UpdateCarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_update_car, container, false);
        tv_commit = inflate.findViewById(R.id.tv_commit_my);
        tv_commit.setOnClickListener(this::onClick);
        car_number = inflate.findViewById(R.id.car_number_et);
        unlock = inflate.findViewById(R.id.tv_unlock);
        unlock.setOnClickListener(this::onClick);

        return inflate;
    }

    public void initDatas() {
        if (TextUtils.isEmpty(SPUtils.getValue("CarNumberMs"))) {
            tv_commit.setText("绑定");
        } else {
            car_number.setEnabled(false);
            car_number.setClickable(false);
            car_number.setText(SPUtils.getValue("CarNumberMs") + "");
            tv_commit.setText("已绑定");
            tv_commit.setEnabled(false);
            tv_commit.setClickable(false);
            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_unlock:
                car_number.setText("");
                tv_commit.setEnabled(true);
                tv_commit.setText("绑定");
                tv_commit.setClickable(true);
                car_number.setEnabled(true);
                car_number.setClickable(true);
                ToastUtils.showSingleToast("请重新输入要绑定的车辆标识");
                return;
            case R.id.tv_commit_my:
                SPUtils.saveValueToDefaultSpByApply("CarNumberMs", car_number.getText().toString().trim());
                ToastUtils.showSingleToast("绑定的标识" + car_number.getText().toString().trim());
                car_number.setEnabled(false);
                car_number.setClickable(false);
                tv_commit.setText("已绑定");
                tv_commit.setEnabled(false);
                tv_commit.setClickable(false);
        }
    }
}