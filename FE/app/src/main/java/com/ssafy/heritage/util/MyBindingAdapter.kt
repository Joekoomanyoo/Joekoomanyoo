package com.ssafy.heritage.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.heritage.R
import com.ssafy.heritage.util.DateFormatter.formatChatDate

@BindingAdapter("glide")
fun setImage(v: ImageView, url: String) {
    if (!url.isNullOrEmpty() && url != "0" && url != "null") {
        Glide.with(v.context)
            .load(url)
            .into(v)
    } else {
        Glide.with(v.context)
            .load(R.drawable.image_ready)
            .into(v)
    }
}

@BindingAdapter("glide_group")
fun setImageGroup(v: ImageView, url: String) {
    if (!url.isNullOrEmpty() && url != "0" && url != "null") {
        Glide.with(v.context)
            .load(url)
            .into(v)
    } else {
        Glide.with(v.context)
            .load(R.drawable.bong)
            .into(v)
    }
}

@BindingAdapter("setTime")
fun setTime(v: TextView, s: String) {
    v.text = s.formatChatDate().substring(5)
}

@BindingAdapter("setDrawable")
fun setDrawable(v: ImageView, s: Int) {
    v.setImageResource(s)
}

@BindingAdapter("calAge")
fun calAge(v: TextView, s: String) {
    var age = 2023 - s.toInt()

    age /= age

    age *= 10

    v.text = if (age < 10) "어린이" else "${age}대"

}