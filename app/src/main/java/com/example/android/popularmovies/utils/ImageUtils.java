package com.example.android.popularmovies.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Aleksandrs Vitjukovs on 5/31/2017.
 */

public class ImageUtils {
    public static void loadUrlIntoImageView(Context context, String url, int placeholderResId, ImageView imageView) {
        Picasso
                .with(context)
                .load(url)
                .error(placeholderResId)
                .placeholder(placeholderResId)
                .into(imageView);
    }
}
