package com.me.kt_pixabay_gallery.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Picture::class], version = 1)
abstract class PictureDataBase: RoomDatabase() {
    abstract fun pictureDao(): PictureDao
}