package com.me.kt_pixabay_gallery.util

import com.me.kt_pixabay_gallery.BuildConfig.PIXABAY_API_KEY


object Util {
    const val BASE_URL = "https://pixabay.com/"
    const val API_KEY = PIXABAY_API_KEY

    val <T> T.exhaustive: T
        get() = this
}