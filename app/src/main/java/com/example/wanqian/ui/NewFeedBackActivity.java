package com.example.wanqian.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.wanqian.R;
import com.example.wanqian.newbase.NewBaseActivity;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.presenter.NewFeedPresenter;
import com.example.wanqian.utitls.AndroidLiuHaiUtils;
import com.example.wanqian.utitls.ToastUtils;
import com.example.wanqian.utitls.Utils;
import com.example.wanqian.widget.SelectedImageGridView;
import com.lcw.library.imagepicker.ImagePicker;
import com.lcw.library.imagepicker.manager.SelectionManager;
import com.lcw.library.imagepicker.utils.GlideImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class NewFeedBackActivity extends NewBaseActivity<NewFeedPresenter> implements SelectedImageGridView.OnAddClickListener,SelectedImageGridView.OnItemClickListener, BaseBckDataView {

    @BindView(R.id.tv_contact)
    EditText tv_contact;
    @BindView(R.id.tv_tel)
    EditText tv_tel;
    @BindView(R.id.selected_images)
    SelectedImageGridView selected_images;
    @BindView(R.id.toolbar_back)
    LinearLayout toolbar_back;
    @BindView(R.id.tv_commit)
    TextView tv_commit;
    @BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar toolbar;
    private static final int REQUEST_CODE_PHOTO = 0x01;//点击拍照标识
    private static final int REQUEST_CODE_CAPTURE = 0x02;//点击拍照标识


    @Override
    protected NewFeedPresenter createPresenter() {
        return new NewFeedPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void configViews() {
        toolbar_back.setOnClickListener(this::otherViewClick);
        tv_commit.setOnClickListener(this::otherViewClick);
    }

    @Override
    public void initDatas() {
        selected_images.setOnAddClickListener(this);
        selected_images.setOnItemClickListener(this);
    }

    @Override
    protected void otherViewClick(View view) {
         switch (view.getId()){
             case R.id.toolbar_back:
                 finish();
                 break;
             case R.id.tv_commit:
                 if (checking()){
                     List<File> files=new ArrayList<>();
                     for (int i=0;i<selected_images.getPaths().size();i++){
                         files.add(new File(selected_images.getPaths().get(i)));
                     }
                     mPresenter.UserFeedBack(tv_contact.getText().toString().trim(),tv_tel.getText().toString().trim(),files);
                 }
                 break;
         }
    }
    public boolean checking(){
        if (Utils.isBlank(tv_contact.getText().toString().trim())){
            ToastUtils.showSingleToast("请输入反馈内容");
            return false;
        }
        if (Utils.isBlank(tv_tel.getText().toString().trim())){
            ToastUtils.showSingleToast("请输入联系方式");
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK && data != null) {
                List<String> images = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                selected_images.setPaths(images);
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> list = new ArrayList<>(SelectionManager.getInstance().getSelectPaths());
                selected_images.setPaths(list);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onAddClick() {
        ImagePicker.getInstance()
                .setImagePaths(selected_images.getPaths())
                .showCamera(true)
                .showImage(true)
                .showVideo(false)
                .showVideoCamera(false)
                .setMaxCount(3)
                .setSingleType(true)
                .setImageLoader(new GlideImageLoader())
                .start(this, REQUEST_CODE_PHOTO);
    }

    @Override
    public void onItemClick(int position) {
        ImagePicker.getInstance()
                .setImagePaths(selected_images.getPaths())
                .showCamera(true)
                .showImage(true)
                .showVideo(false)
                .showVideoCamera(false)
                .setMaxCount(3)
                .setSingleType(true)
                .setImageLoader(new GlideImageLoader())
                .start(this, REQUEST_CODE_PHOTO);
    }

    @Override
    public void getBaseData(BaseModel baseModel) {
        ToastUtils.showSingleToast(baseModel.getMsg());
        finish();
    }

    @Override
    public void getErrorMsg(String msg) {
        ToastUtils.showSingleToast(msg);
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
}
