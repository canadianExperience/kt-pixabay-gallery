package com.me.kt_pixabay_gallery.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertPicture(picture: Picture)

    @Delete
    suspend fun deletePicture(picture: Picture)

    @Query("SELECT * FROM pictures_table")
    fun getPictures(): Flow<List<Picture>>
}