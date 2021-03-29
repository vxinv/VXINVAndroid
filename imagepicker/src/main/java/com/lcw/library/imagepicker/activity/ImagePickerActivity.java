package com.lcw.library.imagepicker.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lcw.library.imagepicker.ImagePicker;
import com.lcw.library.imagepicker.R;
import com.lcw.library.imagepicker.adapter.ImageFoldersAdapter;
import com.lcw.library.imagepicker.adapter.ImagePickerAdapter;
import com.lcw.library.imagepicker.data.MediaFile;
import com.lcw.library.imagepicker.data.MediaFolder;
import com.lcw.library.imagepicker.executors.CommonExecutor;
import com.lcw.library.imagepicker.listener.MediaLoadCallback;
import com.lcw.library.imagepicker.manager.ConfigManager;
import com.lcw.library.imagepicker.manager.SelectionManager;
import com.lcw.library.imagepicker.provider.ImagePickerProvider;
import com.lcw.library.imagepicker.task.ImageLoadTask;
import com.lcw.library.imagepicker.task.MediaLoadTask;
import com.lcw.library.imagepicker.task.VideoLoadTask;
import com.lcw.library.imagepicker.utils.MediaFileUtil;
import com.lcw.library.imagepicker.utils.PermissionUtil;
import com.lcw.library.imagepicker.view.ImageFolderPopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 多图选择器主页面
 * Create by: chenWei.li
 * Date: 2018/8/23
 * Time: 上午1:10
 * Email: lichenwei.me@foxmail.com
 */
public class ImagePickerActivity extends AppCompatActivity implements ImagePickerAdapter.OnItemClickListener,
        ImageFoldersAdapter.OnImageFolderChangeListener {

    /**
     * 启动参数
     */
    private boolean isShowCamera;
    private boolean isShowVideoCamera;
    private boolean isShowImage;
    private boolean isShowVideo;
    private boolean isSingleType;
    private int mMaxCount;
    private List<String> mImagePaths;

    /**
     * 界面UI
     */
    private View mFlActionBar;
    private TextView mTvImageFolders;
    private TextView mTvCommit;
    private RecyclerView mRecyclerView;
    private ImageFolderPopupWindow mImageFolderPopupWindow;
    private ProgressDialog mProgressDialog;

    private GridLayoutManager mGridLayoutManager;
    private ImagePickerAdapter mImagePickerAdapter;

    //图片数据源
    private List<MediaFile> mMediaFileList;
    //文件夹数据源
    private List<MediaFolder> mMediaFolderList;

    //表示屏幕亮暗
    private static final int LIGHT_OFF = 0;
    private static final int LIGHT_ON = 1;

    /**
     * 大图预览页相关
     */
    private static final int REQUEST_SELECT_IMAGES_CODE = 0x01;//用于在大图预览页中点击提交按钮标识


    /**
     * 拍照相关
     */
    private String mFilePath;
    private static final int REQUEST_CODE_CAPTURE = 0x02;//点击拍照标识

    /**
     * 权限相关
     */
    private static final int REQUEST_PERMISSION_CAMERA_CODE = 0x03;

    private boolean camera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepicker);
        initConfig();
        initView();
        initListener();
        getData();
    }

    /**
     * 初始化配置
     */
    protected void initConfig() {
        isShowCamera = ConfigManager.getInstance().isShowCamera();
        isShowVideoCamera = ConfigManager.getInstance().isShowVideoCamera();
        isShowImage = ConfigManager.getInstance().isShowImage();
        isShowVideo = ConfigManager.getInstance().isShowVideo();
        isSingleType = ConfigManager.getInstance().isSingleType();
        mMaxCount = ConfigManager.getInstance().getMaxCount();
        SelectionManager.getInstance().setMaxCount(mMaxCount);

        //载入历史选择记录
        mImagePaths = ConfigManager.getInstance().getImagePaths();
        if (mImagePaths != null && !mImagePaths.isEmpty()) {
            SelectionManager.getInstance().addImagePathsToSelectList(mImagePaths);
        }
        camera=getIntent().getBooleanExtra("camera",false);
        if (camera){
            showCamera(false);
        }
    }


    /**
     * 初始化布局控件
     */
    protected void initView() {
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.scanner_image));

        //顶部栏相关
        mFlActionBar = findViewById(R.id.fl_actionBar);
        mTvImageFolders = findViewById(R.id.tv_actionBar_folders);
        boolean is=ConfigManager.getInstance().isShowCamera();
        if (is){
            mTvImageFolders.setText(this.getString(R.string.all_media));
            mTvImageFolders.setEnabled(true);
        }else{
            mTvImageFolders.setEnabled(false);
            mTvImageFolders.setText(this.getString(R.string.all_video));
        }
        mTvCommit = findViewById(R.id.tv_actionBar_commit);

        //列表相关
        mRecyclerView = findViewById(R.id.rv_main_images);
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        //注释说当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小。
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setItemViewCacheSize(60);

        mMediaFileList = new ArrayList<>();
        mImagePickerAdapter = new ImagePickerAdapter(this, mMediaFileList);
        mImagePickerAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mImagePickerAdapter);
    }

    /**
     * 初始化控件监听事件
     */
    protected void initListener() {
        findViewById(R.id.tv_actionBar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        mTvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitSelection();
            }
        });

        mTvImageFolders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageFolderPopupWindow != null) {
//                    setLightMode(LIGHT_OFF);
                    mImageFolderPopupWindow.showAsDropDown(mFlActionBar, 0, 0);
                }
            }
        });
    }

    /**
     * 获取数据源
     */
    protected void getData() {
        //进行权限的判断
        boolean hasPermission = PermissionUtil.checkPermission(this);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CAMERA_CODE);
        } else {
            startScannerTask();
        }
    }

    /**
     * 权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA_CODE) {
            if (grantResults.length >= 1) {
                int cameraResult = grantResults[0];//相机权限
                int sdResult = grantResults[1];//sd卡权限
                boolean cameraGranted = cameraResult == PackageManager.PERMISSION_GRANTED;//拍照权限
                boolean sdGranted = sdResult == PackageManager.PERMISSION_GRANTED;//拍照权限
                if (cameraGranted && sdGranted) {
                    //具有拍照权限，sd卡权限，开始扫描任务
                    startScannerTask();
                } else {
                    //没有权限
                    Toast.makeText(this, getString(R.string.permission_tip), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }


    /**
     * 开启扫描任务
     */
    private void startScannerTask() {
        Runnable mediaLoadTask = null;

        //照片、视频全部加载
        if (isShowImage && isShowVideo) {
            mediaLoadTask = new MediaLoadTask(this, new MediaLoader());
        }

        //只加载视频
        if (!isShowImage && isShowVideo) {
            mediaLoadTask = new VideoLoadTask(this, new MediaLoader());
        }

        //只加载图片
        if (isShowImage && !isShowVideo) {
            mediaLoadTask = new ImageLoadTask(this, new MediaLoader());
        }

        //不符合以上场景，采用照片、视频全部加载
        if (mediaLoadTask == null) {
            mediaLoadTask = new MediaLoadTask(this, new MediaLoader());
        }

        CommonExecutor.getInstance().execute(mediaLoadTask);
    }


    /**
     * 处理媒体数据加载成功后的UI渲染
     */
    class MediaLoader implements MediaLoadCallback {

        @Override
        public void loadMediaSuccess(final List<MediaFolder> mediaFolderList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!mediaFolderList.isEmpty()) {
                        //默认加载全部照片
                        mMediaFileList.addAll(mediaFolderList.get(0).getMediaFileList());
                        mImagePickerAdapter.notifyDataSetChanged();

                        //图片文件夹数据
                        mMediaFolderList = new ArrayList<>(mediaFolderList);
                        mImageFolderPopupWindow = new ImageFolderPopupWindow(ImagePickerActivity.this, mMediaFolderList);
                        mImageFolderPopupWindow.setAnimationStyle(R.style.imageFolderAnimator);
                        mImageFolderPopupWindow.getAdapter().setOnImageFolderChangeListener(ImagePickerActivity.this);
                        mImageFolderPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
//                                setLightMode(LIGHT_ON);
                            }
                        });
                        updateCommitButton();
                    }
                    mProgressDialog.cancel();
                }
            });
        }
    }

    /**
     * 设置屏幕的亮度模式
     *
     * @param lightMode
     */
    private void setLightMode(int lightMode) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        switch (lightMode) {
            case LIGHT_OFF:
                layoutParams.alpha = 0.7f;
                break;
            case LIGHT_ON:
                layoutParams.alpha = 1.0f;
                break;
            default:
                break;
        }
        getWindow().setAttributes(layoutParams);
    }

    /**
     * 点击图片
     *
     * @param view
     * @param position
     */
    @Override
    public void onMediaClick(View view, int position) {
        if (isShowCamera&&isShowVideoCamera){
            if (position==0){
                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                if (!selectPathList.isEmpty() && MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
                    return;
                }
                if (!SelectionManager.getInstance().isCanChoose()) {
                    Toast.makeText(this, String.format(getString(R.string.select_image_max), mMaxCount), Toast.LENGTH_SHORT).show();
                    return;
                }
                showCamera(false);
                return;
            }
            if (position==1){
                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                if (!selectPathList.isEmpty() && !MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
                    return;
                }
                if (selectPathList.size() >= ConfigManager.getInstance().getMaxVideo()) {
                    Toast.makeText(this, String.format(getString(R.string.select_video_max), ConfigManager.getInstance().getMaxVideo()), Toast.LENGTH_SHORT).show();
                    return;
                }
                showCamera(true);
                return;
            }
            position-=2;
        }else if (isShowCamera&&isShowVideoCamera==false){

            if (position==0){
                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                if (!selectPathList.isEmpty() && MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
                    return;
                }
                if (!SelectionManager.getInstance().isCanChoose()) {
                    Toast.makeText(this, String.format(getString(R.string.select_image_max), mMaxCount), Toast.LENGTH_SHORT).show();
                    return;
                }
                showCamera(false);
                return;
            }
            position-=1;
        }else if (isShowVideoCamera&&isShowCamera==false){
            if (position==0){
                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                if (!selectPathList.isEmpty() && !MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
                    return;
                }
                if (selectPathList.size() >= ConfigManager.getInstance().getMaxVideo()) {
                    Toast.makeText(this, String.format(getString(R.string.select_video_max), ConfigManager.getInstance().getMaxVideo()), Toast.LENGTH_SHORT).show();
                    return;
                }
                showCamera(true);
                return;
            }
            position-=1;
        }
        if (mMediaFileList != null) {
            ImagePreActivity.launch(this, mMediaFileList, position, REQUEST_SELECT_IMAGES_CODE);
        }
    }

    /**
     * 选中/取消选中图片
     *
     * @param view
     * @param position
     */
    @Override
    public void onMediaCheck(View view, int position) {
        if (isShowCamera&&isShowVideoCamera){
            if (position==0){
                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                if (!selectPathList.isEmpty() && MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
                    return;
                }
                if (!SelectionManager.getInstance().isCanChoose()) {
                    Toast.makeText(this, String.format(getString(R.string.select_image_max), mMaxCount), Toast.LENGTH_SHORT).show();
                    return;
                }
                showCamera(false);
                return;
            }
            if (position==1){
                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                if (!selectPathList.isEmpty() && !MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
                    return;
                }
                if (selectPathList.size() >= ConfigManager.getInstance().getMaxVideo()) {
                    Toast.makeText(this, String.format(getString(R.string.select_video_max), ConfigManager.getInstance().getMaxVideo()), Toast.LENGTH_SHORT).show();
                    return;
                }
                showCamera(true);
                return;
            }
        }else if (isShowCamera&&isShowVideoCamera==false){

            if (position==0){
                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                if (!selectPathList.isEmpty() && MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
                    return;
                }
                if (!SelectionManager.getInstance().isCanChoose()) {
                    Toast.makeText(this, String.format(getString(R.string.select_image_max), mMaxCount), Toast.LENGTH_SHORT).show();
                    return;
                }
                showCamera(false);
                return;
            }
        }else if (isShowVideoCamera&&isShowCamera==false){
            if (position==0){
                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                if (!selectPathList.isEmpty() && !MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
                    return;
                }
                if (selectPathList.size() >= ConfigManager.getInstance().getMaxVideo()) {
                    Toast.makeText(this, String.format(getString(R.string.select_video_max), ConfigManager.getInstance().getMaxVideo()), Toast.LENGTH_SHORT).show();
                    return;
                }
                showCamera(true);
                return;
            }
        }
//        if (isShowCamera) {
//            if (position == 0) {
//                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
//                if (!selectPathList.isEmpty() && MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
//                    return;
//                }
//                if (!SelectionManager.getInstance().isCanChoose()) {
//                    Toast.makeText(this, String.format(getString(R.string.select_image_max), mMaxCount), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                showCamera(false);
//                return;
//            }
//            if (position == 1) {
//                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
//                if (!selectPathList.isEmpty() && !MediaFileUtil.isVideoFileType(selectPathList.get(0))) {
//                    return;
//                }
//                if (selectPathList.size() >= ConfigManager.getInstance().getMaxVideo()) {
//                    Toast.makeText(this, String.format(getString(R.string.select_video_max), ConfigManager.getInstance().getMaxVideo()), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                showCamera(true);
//                return;
//            }
//        }

        //执行选中/取消操作
        MediaFile mediaFile = mImagePickerAdapter.getMediaFile(position);
        if (mediaFile != null) {
            String imagePath = mediaFile.getPath();
            if (isSingleType) {
                //单类型选取，判断添加类型
                ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                if (!selectPathList.isEmpty()) {
                    //判断选中集合中第一项是否为视频
                    String path = selectPathList.get(0);
                    boolean isVideo = MediaFileUtil.isVideoFileType(path);
                    if ((!isVideo && mediaFile.getDuration() != 0) || isVideo && mediaFile.getDuration() == 0) {
                        //类型不同
//                        Toast.makeText(this, getString(R.string.single_type_choose), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            boolean addSuccess = SelectionManager.getInstance().addImageToSelectList(imagePath);
            if (addSuccess) {
                mImagePickerAdapter.notifyDataSetChanged();
            }
        }
        updateCommitButton();
    }

    /**
     * 更新确认按钮状态
     */
    private void updateCommitButton() {
        //改变确定按钮UI
        int selectCount = SelectionManager.getInstance().getSelectPaths().size();
        if (selectCount == 0) {
            mTvCommit.setEnabled(false);
            mTvCommit.setText(getString(R.string.confirm));
            return;
        }
        boolean isVideo = MediaFileUtil.isVideoFileType(SelectionManager.getInstance().getSelectPaths().get(0));
        if (isVideo) {
            mTvCommit.setEnabled(true);
            mTvCommit.setText(String.format(getString(R.string.confirm_msg), selectCount, ConfigManager.getInstance().getMaxVideo()));
        } else {
            mTvCommit.setEnabled(true);
            mTvCommit.setText(String.format(getString(R.string.confirm_msg), selectCount, mMaxCount));
        }
    }

    /**
     * 跳转相机拍照
     */
    private void showCamera(boolean record) {
        if (record) {
            File fileDir = new File(Environment.getExternalStorageDirectory(), "Videos");
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            mFilePath = fileDir.getAbsolutePath() + "/VID_" + System.currentTimeMillis() + ".mp4";

            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this, ImagePickerProvider.getFileProviderName(this), new File(mFilePath));
            } else {
                uri = Uri.fromFile(new File(mFilePath));
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE);
        } else {
            //拍照存放路径
            File fileDir = new File(Environment.getExternalStorageDirectory(), "Pictures");
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            mFilePath = fileDir.getAbsolutePath() + "/IMG_" + System.currentTimeMillis() + ".jpg";

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this, ImagePickerProvider.getFileProviderName(this), new File(mFilePath));
            } else {
                uri = Uri.fromFile(new File(mFilePath));
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE);
        }
    }

    /**
     * 当图片文件夹切换时，刷新图片列表数据源
     *
     * @param view
     * @param position
     */
    @Override
    public void onImageFolderChange(View view, int position) {
        MediaFolder mediaFolder = mMediaFolderList.get(position);
        //更新当前文件夹名
        String folderName = mediaFolder.getFolderName();
        if (!TextUtils.isEmpty(folderName)) {
            mTvImageFolders.setText(folderName);
        }
        //更新图片列表数据源
        mMediaFileList.clear();
        mMediaFileList.addAll(mediaFolder.getMediaFileList());
        mImagePickerAdapter.notifyDataSetChanged();

        mImageFolderPopupWindow.dismiss();
    }

    /**
     * 拍照回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAPTURE) {
                //通知媒体库刷新
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + mFilePath)));
                //添加到选中集合
                SelectionManager.getInstance().addImageToSelectList(mFilePath);

                ArrayList<String> list = new ArrayList<>(SelectionManager.getInstance().getSelectPaths());
                Intent intent = new Intent();
                intent.putStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES, list);
                setResult(RESULT_OK, intent);
                finish();
            }

            if (requestCode == REQUEST_SELECT_IMAGES_CODE) {
                commitSelection();
            }
        }
    }

    /**
     * 选择图片完毕，返回
     */
    private void commitSelection() {
        ArrayList<String> list = new ArrayList<>(SelectionManager.getInstance().getSelectPaths());
        Intent intent = new Intent();
        intent.putStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES, list);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mImagePickerAdapter.notifyDataSetChanged();
        updateCommitButton();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SelectionManager.getInstance().removeAll();//清空选中记录
        try {
            ConfigManager.getInstance().getImageLoader().clearMemoryCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
