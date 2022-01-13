package com.me.kt_pixabay_gallery.apimanager.model

data class PictureResponse(
    val hits: List<PictureResult>,
    val total: Int,
    val totalHits: Int

)

//https://pixabay.com/api/docs/
