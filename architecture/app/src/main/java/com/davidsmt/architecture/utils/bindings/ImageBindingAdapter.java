package com.davidsmt.architecture.utils.bindings;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by d.san.martin.torres on 7/11/17.
 */

public class ImageBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

}

