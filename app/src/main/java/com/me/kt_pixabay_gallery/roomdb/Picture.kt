package com.me.kt_pixabay_gallery.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pictures_table")
data class Picture(
    var url: String,
    var isFavorite: Boolean,

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
