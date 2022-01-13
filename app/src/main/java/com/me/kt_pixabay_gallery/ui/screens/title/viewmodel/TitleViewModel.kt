package com.me.kt_pixabay_gallery.ui.screens.title.viewmodel

import androidx.lifecycle.*
import com.me.kt_pixabay_gallery.apimanager.model.PictureResponse
import com.me.kt_pixabay_gallery.repository.PictureRepositoryInterface
import com.me.kt_pixabay_gallery.roomdb.Picture
import com.me.kt_pixabay_gallery.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleViewModel
@Inject constructor(
    private val repository: PictureRepositoryInterface
)
    : ViewModel() {

    fun updateFavorite(isFavorite: Boolean, id: Int) = viewModelScope.launch {
        repository.updatePictureIsFavorite(isFavorite, id)
    }

    private val picturesFlow = repository.getPictures()
    val pictures: LiveData<List<Picture>> get() = picturesFlow.asLiveData()
}