package com.example.wanqian.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.example.wanqian.R;
import com.example.wanqian.utitls.ImageLoadUtil;
import com.example.wanqian.utitls.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectedImageGridView extends ViewGroup {

    private static final String ITEM_TAG = "item";

    private final ArrayList<String> mImagePaths = new ArrayList<>();

    /**
     * 图片最多张数
     */
    private int mMaxCount;
    /**
     * 每行显示数量
     */
    private int mColumnCount;

    private int mColumnSpace;
    private int mRowSpace;

    private int mItemImageId;
    private int mItemDelId;
    private Drawable mDelIcon;
    private FrameLayout mAddView;
    private OnItemClickListener mItemClickListener;
    private OnImagesCountChangeListener mCountChangeListener;
    private OnAddClickListener mAddClickListener;

    public SelectedImageGridView(@NonNull Context context) {
        this(context, null);
    }

    public SelectedImageGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SelectedImageGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMaxCount = 3;
        mColumnCount = 3;
        mColumnSpace = ScreenUtils.dp2px(context, 12);
        mRowSpace = ScreenUtils.dp2px(context, 12);

        mItemImageId = generateViewId();
        mItemDelId = generateViewId();
        mDelIcon = getResources().getDrawable(R.mipmap.fd_close);
        mAddView = createItemView();
        mAddView.setOnClickListener(new AddClickListener());
        ImageView addImageView = mAddView.findViewById(mItemImageId);
        addImageView.setImageResource(R.mipmap.fd_add);
        mAddView.findViewById(mItemDelId).setVisibility(INVISIBLE);
        addView(mAddView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int usableWidth = width - getPaddingLeft() - getPaddingRight() - (mColumnCount - 1) * mColumnSpace;
        int itemSize = usableWidth / mColumnCount;

        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View itemView = getChildAt(index);
            itemView.measure(MeasureSpec.makeMeasureSpec(itemSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemSize, MeasureSpec.EXACTLY));
        }

        int rowCount = childCount / mColumnCount;
        rowCount = childCount % mColumnCount == 0 ? rowCount : rowCount + 1;
        int height = getPaddingTop() + getPaddingBottom() + itemSize * rowCount + (rowCount - 1) * mRowSpace;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        int parentLeft = getPaddingLeft();
        int parentTop = getPaddingTop();

        int row, column, childLeft, childTop;
        for (int index = 0; index < childCount; index++) {
            View itemView = getChildAt(index);
            int width = itemView.getMeasuredWidth();
            int height = itemView.getMeasuredHeight();

            column = index % mColumnCount;
            childLeft = parentLeft + column * width + column * mColumnSpace;

            row = index / mColumnCount;
            childTop = parentTop + row * height + row * mRowSpace;

            itemView.layout(childLeft, childTop, childLeft + width, childTop + height);
        }
    }

    public void setPaths(List<String> paths) {
        this.mImagePaths.clear();
        if (paths!=null){
            int count = Math.min(paths.size(), mMaxCount);
            for (int i = 0; i < count; i++) {
                mImagePaths.add(paths.get(i));
            }
        }

        notifyPathSetChanged();
    }

    public ArrayList<String> getPaths() {
        return new ArrayList<>(mImagePaths);
    }

    public void addPath(String path) {
        addPath(mImagePaths.size(), path);
    }

    public void addPath(int index, String path) {
        if (mImagePaths.size() + 1 > mMaxCount) {
            return;
        }
        mImagePaths.add(index, path);
        notifyPathInserted(index);
    }

    public void removePath(int index) {
        mImagePaths.remove(index);
        notifyPathRemoved(index);
    }

    public void replacePath(int index, String path) {
        mImagePaths.set(index, path);
        notifyPathChanged(index);
    }

    private void notifyPathSetChanged() {
        removeAllViews();
        for (String path : mImagePaths) {
            FrameLayout itemView = createItemView();
            addView(itemView);
            ImageView imageView = itemView.findViewById(mItemImageId);
            ImageLoadUtil.loadPic(getContext(),path,imageView);
        }
        if (mImagePaths.size() < mMaxCount) {
            addView(mAddView);
        }
        if (mCountChangeListener != null) {
            mCountChangeListener.onImagesCountChange(mImagePaths.size());
        }
    }

    private void notifyPathInserted(int index) {
        FrameLayout itemView = createItemView();
        addView(itemView, index);
        String path = mImagePaths.get(index);
        ImageView imageView = itemView.findViewById(mItemImageId);
        ImageLoadUtil.loadPic(getContext(),path,imageView);
        if (mImagePaths.size() >= mMaxCount) {
            removeView(mAddView);
        }
        if (mCountChangeListener != null) {
            mCountChangeListener.onImagesCountChange(mImagePaths.size());
        }
    }

    private void notifyPathRemoved(int index) {
        removeViewAt(index);
        if (mImagePaths.size() == mMaxCount - 1) {
            addView(mAddView);
        }
        if (mCountChangeListener != null) {
            mCountChangeListener.onImagesCountChange(mImagePaths.size());
        }
    }

    private void notifyPathChanged(int index) {
        View itemView = getChildAt(index);
        String path = mImagePaths.get(index);
        ImageView imageView = itemView.findViewById(mItemImageId);
        ImageLoadUtil.loadPic(getContext(),path,imageView);
    }

    private FrameLayout createItemView() {
        FrameLayout itemLayout = new FrameLayout(getContext());
        itemLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        itemLayout.setOnClickListener(new ItemClickListener());
        itemLayout.setTag(ITEM_TAG);

        ImageView imageView = new ImageView(getContext());
        imageView.setId(mItemImageId);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(imageParams);
        itemLayout.addView(imageView);

        ImageView delView = new ImageView(getContext());
        delView.setId(mItemDelId);
        delView.setOnClickListener(new DeleteClickListener());
        FrameLayout.LayoutParams delParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        delParams.gravity = Gravity.END | Gravity.TOP;
        delView.setLayoutParams(delParams);
        delView.setImageDrawable(mDelIcon);
        int padding = ScreenUtils.dp2px(getContext(), 6);
        delView.setPadding(0, padding, padding, 0);
        itemLayout.addView(delView);

        return itemLayout;
    }

    private class AddClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (mAddClickListener != null) {
                mAddClickListener.onAddClick();
            }
        }
    }

    private class DeleteClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            int position = getItemIndex(v);
            if (position != -1) {
                removePath(position);
            }
        }
    }

    private class ItemClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int position = getItemIndex(v);
                mItemClickListener.onItemClick(position);
            }
        }
    }

    /**
     * 获取itemView位置
     *
     * @param view itemView或者子view
     * @return 如果找不到将返回-1
     */
    private int getItemIndex(View view) {
        if (ITEM_TAG.equals(view.getTag())) {
            int index = -1;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (getChildAt(i) == view) {
                    index = i;
                    break;
                }
            }
            return index;
        }
        ViewParent parent = view.getParent();
        if (parent == null) {
            return -1;
        }
        return getItemIndex((ViewGroup) parent);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnImagesCountChangeListener {
        void onImagesCountChange(int selectedCount);
    }

    public void setOnImagesCountChangeListener(OnImagesCountChangeListener l) {
        this.mCountChangeListener = l;
    }

    public interface OnAddClickListener {
        void onAddClick();
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        this.mAddClickListener = listener;
    }

    public int getImageCount() {
        return mImagePaths.size();
    }
}
