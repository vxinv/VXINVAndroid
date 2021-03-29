package com.lcw.library.imagepicker.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.lcw.library.imagepicker.R;

/**
 * Glide图片加载实现
 *
 * @author jiangyujiang
 * @date 2019-05-24
 */
public class GlideImageLoader implements ImageLoader {

    private RequestOptions mOptions = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(R.mipmap.icon_image_default)
            .error(R.mipmap.icon_image_error);

    private RequestOptions mPreOptions = new RequestOptions()
            .skipMemoryCache(true)
            .error(R.mipmap.icon_image_error);

    @Override
    public void loadImage(ImageView imageView, String imagePath) {
        Glide.with(imageView.getContext()).load(imagePath).apply(mOptions).into(imageView);
    }

    @Override
    public void loadPreImage(ImageView imageView, String imagePath) {
        Glide.with(imageView.getContext()).load(imagePath).apply(mPreOptions).into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        // 等Glide自己清理
    }
}
