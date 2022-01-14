package com.me.kt_pixabay_gallery.util

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.me.kt_pixabay_gallery.BuildConfig.PIXABAY_API_KEY
import com.me.kt_pixabay_gallery.R


object Util {
    const val BASE_URL = "https://pixabay.com/"
    const val API_KEY = PIXABAY_API_KEY

    val <T> T.exhaustive: T
        get() = this

    fun getProgressDrawable(context: Context): CircularProgressDrawable {
        val color = context.themeColor(R.attr.colorSecondary)
        return CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 30f
            setColorSchemeColors(color, color)
            start()
        }
    }

    private fun Context.themeColor(@AttrRes attrRes: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute (attrRes, typedValue, true)
        return typedValue.data
    }
}