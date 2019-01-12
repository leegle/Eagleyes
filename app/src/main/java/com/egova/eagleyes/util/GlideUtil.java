package com.egova.eagleyes.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.egova.eagleyes.R;

public class GlideUtil {
    public static void load(Context context, ImageView imageView, Object url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_picture_loading)
                .error(R.mipmap.ic_picture_loadfailed).priority(Priority.HIGH)
                .centerInside();
        Glide.with(context).load(url).apply(requestOptions).thumbnail(0.2f).into(imageView);

    }
}
