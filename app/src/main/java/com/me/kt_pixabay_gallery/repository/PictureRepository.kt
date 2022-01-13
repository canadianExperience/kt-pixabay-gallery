package com.me.kt_pixabay_gallery.repository

import com.me.kt_pixabay_gallery.apimanager.api.RetrofitAPI
import com.me.kt_pixabay_gallery.apimanager.model.PictureResponse
import com.me.kt_pixabay_gallery.roomdb.Picture
import com.me.kt_pixabay_gallery.roomdb.PictureDao
import kotlinx.coroutines.flow.Flow
import com.me.kt_pixabay_gallery.util.Resource
import javax.inject.Inject

class PictureRepository @Inject constructor(
    private val pictureDao: PictureDao,
    private val retrofitAPI: RetrofitAPI
) : PictureRepositoryInterface {

    override suspend fun insertPicture(picture: Picture) {
        pictureDao.insertPicture(picture)
    }

    override suspend fun deletePicture(picture: Picture) {
        pictureDao.deletePicture(picture)
    }

    override suspend fun updatePictureIsFavorite(bool: Boolean, id: Int) {
        pictureDao.updatePictureIsFavorite(bool, id)
    }

    override fun getPictures(): Flow<List<Picture>> {
        return pictureDao.getPictures()
    }

    override suspend fun searchPicture(urlStr: String): Resource<PictureResponse> {
        return try {
            val response = retrofitAPI.pictureSearch(urlStr)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else{
                Resource.error("Error", null)
            }
        } catch (e: Exception){
            Resource.error("No data", null)
        }
    }
}