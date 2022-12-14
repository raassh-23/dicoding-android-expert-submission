package com.raassh.core.utils

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.raassh.core.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .error(R.drawable.ic_baseline_broken_image_24)
        .into(this)
}

fun String.withDateFormat(): String {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = format.parse(this) as Date
        DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date)
    } catch (e: ParseException) {
        Log.e("withDateFormat", "can't parse $this")
        this
    }
}