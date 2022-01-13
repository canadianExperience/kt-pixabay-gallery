package com.me.kt_pixabay_gallery.apimanager.model

import com.google.gson.annotations.SerializedName

data class PictureResult(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    val largeImageURL: String,
    val fullHDURL: String,
    val imageURL: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    @SerializedName("user_id")
    val userId: Int,
    val user: String,
    val userImageURL: String
)

//https://pixabay.com/api/docs/
