package com.aduran.photos.view

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("bind:imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null && url != "") {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}