package com.me.kt_pixabay_gallery.ui.screens.title.viewmodel

import androidx.lifecycle.*
import com.bumptech.glide.RequestManager
import com.me.kt_pixabay_gallery.repository.PictureRepositoryInterface
import com.me.kt_pixabay_gallery.roomdb.Picture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleViewModel
@Inject constructor(
    private val repository: PictureRepositoryInterface,
    private val glide: RequestManager
) : ViewModel() {

    private val titleEventChannel = Channel<TitleEvent>()
    val titleEvent = titleEventChannel.receiveAsFlow()

    val glideRequestManager get() = glide

    private val isShowFavorites = MutableStateFlow(false)
    val isShowFavoritesLiveData = isShowFavorites.asLiveData()

    fun setIsShowFavorites(){
        isShowFavorites.value = (!isShowFavorites.value)
    }

    fun updateFavorite(isFavorite: Boolean, id: Int) = viewModelScope.launch {
        repository.updatePictureIsFavorite(isFavorite, id)
    }

    fun onAddBtnClick() = viewModelScope.launch {
        titleEventChannel.send(TitleEvent.NavigateToAddPictureFragment)
    }

    fun onPictureClick(url: String, id: Int) = viewModelScope.launch {
        titleEventChannel.send(TitleEvent.NavigateToZoomPictureFragment(url,id))
    }

    @ExperimentalCoroutinesApi
    private val picturesFlow: Flow<List<Picture>> = isShowFavorites.flatMapLatest{
        repository.showPictures(it)
    }

    @ExperimentalCoroutinesApi
    val pictures: LiveData<List<Picture>> get() = picturesFlow.asLiveData()

    sealed class TitleEvent{
        object NavigateToAddPictureFragment : TitleEvent()
        data class NavigateToZoomPictureFragment(val url: String, val id: Int) : TitleEvent()
    }

}