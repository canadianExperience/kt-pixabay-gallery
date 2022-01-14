package com.me.kt_pixabay_gallery.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {
    fun showPictures(isFavorite: Boolean): Flow<List<Picture>> =
        if(isFavorite) getFavoritePictures() else getPictures()

    @Insert(onConflict = REPLACE)
    suspend fun insertPicture(picture: Picture)

    @Delete
    suspend fun deletePicture(picture: Picture)

    @Query("UPDATE pictures_table SET isFavorite=:bool WHERE id=:id")
    suspend fun updatePictureIsFavorite(bool: Boolean, id: Int)

    @Query("SELECT * FROM pictures_table ORDER BY id DESC")
    fun getPictures(): Flow<List<Picture>>

    @Query("SELECT * FROM pictures_table WHERE isFavorite=1 ORDER BY id DESC")
    fun getFavoritePictures(): Flow<List<Picture>>
}