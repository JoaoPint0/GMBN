package com.endeavour.gmbn.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("thumbnail")
fun bindThumbnails(view: ImageView, image: String?) {

    Glide.with(view.context)
        .load(image)
        .into(view)

}

@BindingAdapter("authorPic")
fun bindAuthorPic(view: ImageView, image: String?) {

    Glide.with(view.context)
        .load(image)
        .apply(RequestOptions.circleCropTransform())
        .into(view)

}

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}
