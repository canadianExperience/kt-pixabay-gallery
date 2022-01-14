package com.me.kt_pixabay_gallery.ui.screens.zoom.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ZoomPictureViewModel
@Inject constructor(
    private val glide: RequestManager,
    private val state: SavedStateHandle
) : ViewModel() {

    val url = state.get<String>("url")?:""
    val glideRequestManager get() = glide

}