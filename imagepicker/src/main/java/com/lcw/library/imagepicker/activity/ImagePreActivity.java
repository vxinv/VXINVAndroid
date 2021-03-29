package com.lcw.library.imagepicker.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcw.library.imagepicker.R;
import com.lcw.library.imagepicker.adapter.ImagePreViewAdapter;
import com.lcw.library.imagepicker.data.MediaFile;
import com.lcw.library.imagepicker.manager.ConfigManager;
import com.lcw.library.imagepicker.manager.SelectionManager;
import com.lcw.library.imagepicker.provider.ImagePickerProvider;
import com.lcw.library.imagepicker.utils.DataUtil;
import com.lcw.library.imagepicker.utils.MediaFileUtil;
import com.lcw.library.imagepicker.view.HackyViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 大图预览界面
 * Create by: chenWei.li
 * Date: 2018/10/3
 * Time: 下午11:32
 * Email: lichenwei.me@foxmail.com
 */
public class ImagePreActivity extends AppCompatActivity {
    private static final String IMAGE_POSITION = "imagePosition";

    private List<MediaFile> mMediaFileList;

    private HackyViewPager mViewPager;
    private ImageView mIvPlay;
    private TextView mTvTitle;
    private ImageView mIvPreCheck;
    private TextView mTvSelectCount;
    private TextView mTvCommit;

    public static void launch(Activity activity, List<MediaFile> fileList, int position, int requestCode) {
        DataUtil.getInstance().setMediaData(fileList);
        Intent intent = new Intent(activity, ImagePreActivity.class);
        intent.putExtra(ImagePreActivity.IMAGE_POSITION, position);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_image);
        initView();
        initListener();
        getData();
    }

    protected void initView() {
        mViewPager = findViewById(R.id.vp_main_preImage);
        mIvPlay = findViewById(R.id.iv_main_play);
        mTvTitle = findViewById(R.id.tv_actionBar_title);
        mIvPreCheck = findViewById(R.id.iv_actionBar_check);
        mTvSelectCount = findViewById(R.id.tv_select_count);
        mTvCommit = findViewById(R.id.tv_main_commit);
    }

    protected void initListener() {
        findViewById(R.id.iv_actionBar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvTitle.setText(String.format("%d/%d", position + 1, mMediaFileList.size()));
                setIvPlayShow(mMediaFileList.get(position));
                updateSelectButton(mMediaFileList.get(position).getPath());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIvPreCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaFile mediaFile = mMediaFileList.get(mViewPager.getCurrentItem());
                String imagePath = mediaFile.getPath();
                if (ConfigManager.getInstance().isSingleType()) {
                    //单类型选取，判断添加类型
                    ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                    if (!selectPathList.isEmpty()) {
                        //判断选中集合中第一项是否为视频
                        String path = selectPathList.get(0);
                        boolean isVideo = MediaFileUtil.isVideoFileType(path);
                        if ((!isVideo && mediaFile.getDuration() != 0) || isVideo && mediaFile.getDuration() == 0) {
                            //类型不同
//                            Toast.makeText(ImagePreActivity.this, getString(R.string.single_type_choose), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                boolean addSuccess = SelectionManager.getInstance().addImageToSelectList(imagePath);
                if (addSuccess) {
                    updateSelectButton(imagePath);
                    updateCommitButton();
                }
            }
        });

        mTvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });

        mIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现播放视频的跳转逻辑(调用原生视频播放器)
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = FileProvider.getUriForFile(ImagePreActivity.this, ImagePickerProvider.getFileProviderName(ImagePreActivity.this), new File(mMediaFileList.get(mViewPager.getCurrentItem()).getPath()));
                intent.setDataAndType(uri, "video/*");
                //给所有符合跳转条件的应用授权
                List<ResolveInfo> resInfoList = getPackageManager()
                        .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivity(intent);
            }
        });
    }

    protected void getData() {
        mMediaFileList = DataUtil.getInstance().getMediaData();
        int position = getIntent().getIntExtra(IMAGE_POSITION, 0);
        mTvTitle.setText(String.format("%d/%d", position + 1, mMediaFileList.size()));
        ImagePreViewAdapter imagePreViewAdapter = new ImagePreViewAdapter(this, mMediaFileList);
        mViewPager.setAdapter(imagePreViewAdapter);
        mViewPager.setCurrentItem(position);
        //更新当前页面状态
        setIvPlayShow(mMediaFileList.get(position));
        updateSelectButton(mMediaFileList.get(position).getPath());
        updateCommitButton();
    }

    /**
     * 更新确认按钮状态
     */
    private void updateCommitButton() {

        int maxCount = SelectionManager.getInstance().getMaxCount();

        //改变确定按钮UI
        int selectCount = SelectionManager.getInstance().getSelectPaths().size();
        if (selectCount == 0) {
            mTvCommit.setEnabled(false);
        } else {
            mTvCommit.setEnabled(true);
        }

        if (!SelectionManager.getInstance().getSelectPaths().isEmpty() && MediaFileUtil.isVideoFileType(SelectionManager.getInstance().getSelectPaths().get(0))) {
            mTvSelectCount.setText(String.format(getString(R.string.select_count), selectCount, ConfigManager.getInstance().getMaxVideo()));
        } else {
            mTvSelectCount.setText(String.format(getString(R.string.select_count), selectCount, maxCount));
        }
    }

    /**
     * 更新选择按钮状态
     */
    private void updateSelectButton(String imagePath) {
        boolean isSelect = SelectionManager.getInstance().isImageSelect(imagePath);
        if (isSelect) {
            mIvPreCheck.setImageDrawable(getResources().getDrawable(R.mipmap.icon_image_checked));
        } else {
            mIvPreCheck.setImageDrawable(getResources().getDrawable(R.mipmap.icon_image_check));
        }
    }

    /**
     * 设置是否显示视频播放按钮
     */
    private void setIvPlayShow(MediaFile mediaFile) {
        if (mediaFile.getDuration() > 0) {
            mIvPlay.setVisibility(View.VISIBLE);
        } else {
            mIvPlay.setVisibility(View.GONE);
        }
    }

}
