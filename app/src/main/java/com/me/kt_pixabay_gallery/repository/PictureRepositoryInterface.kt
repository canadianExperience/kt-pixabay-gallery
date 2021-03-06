package com.me.kt_pixabay_gallery.repository

import com.me.kt_pixabay_gallery.apimanager.model.PictureResponse
import com.me.kt_pixabay_gallery.roomdb.Picture
import com.me.kt_pixabay_gallery.util.Resource
import kotlinx.coroutines.flow.Flow

interface PictureRepositoryInterface {
    suspend fun insertPicture(picture: Picture)
    suspend fun deletePicture(id: Int)
    suspend fun updatePictureIsFavorite(bool: Boolean, id: Int)
    fun showPictures(isFavorite: Boolean): Flow<List<Picture>>
    suspend fun searchPicture(urlStr: String): Resource<PictureResponse>
}