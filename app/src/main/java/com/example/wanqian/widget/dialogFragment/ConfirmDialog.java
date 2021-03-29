package com.example.wanqian.widget.dialogFragment;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.wanqian.R;
import com.example.wanqian.widget.dialogFragment.widget.BaseDialog;
import com.example.wanqian.widget.dialogFragment.widget.ViewHolder;


/**
 * 确定样式Dialog
 */
public class ConfirmDialog extends BaseDialog {

    private String type;

    public static ConfirmDialog newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        ConfirmDialog dialog = new ConfirmDialog();
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
        type = bundle.getString("type");
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.pop_locationpop;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseDialog dialog) {
        holder.getView(R.id.tab);
        if ("1".equals(type)) {
            holder.setText(R.id.title, "提示");
            holder.setText(R.id.message, "您已支付成功!");
        } else if ("2".equals(type)) {
            holder.setText(R.id.title, "警告");
            holder.setText(R.id.message, "您的帐号已被冻结!");
        }


    }
}
