package com.me.kt_pixabay_gallery.ui.screens.zoom.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.me.kt_pixabay_gallery.repository.PictureRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZoomPictureViewModel
@Inject constructor(
    private val repository: PictureRepositoryInterface,
    private val glide: RequestManager,
    private val state: SavedStateHandle
) : ViewModel() {

    private val zoomEventChannel = Channel<ZoomEvent>()
    val zoomEvent = zoomEventChannel.receiveAsFlow()

    val url = state.get<String>("url")?:""
    val id = state.get<Int>("id")?:0
    val glideRequestManager get() = glide

    fun onDeletePictureClick() = viewModelScope.launch {
        repository.deletePicture(id)
        zoomEventChannel.send(ZoomEvent.NavigateBackToTitleFragment("$url deleted"))
    }

    sealed class ZoomEvent{
        data class NavigateBackToTitleFragment(val message:String) : ZoomEvent()
    }
}