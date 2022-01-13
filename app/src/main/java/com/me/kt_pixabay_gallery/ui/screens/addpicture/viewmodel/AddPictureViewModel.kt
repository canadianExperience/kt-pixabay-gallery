package com.me.kt_pixabay_gallery.ui.screens.addpicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class AddPictureViewModel @Inject constructor(
    private val repository: PictureRepositoryInterface
): ViewModel() {

    private val addPictureEventChannel = Channel<AddPictureEvent>()
    val addPictureEvent =  addPictureEventChannel.receiveAsFlow()

   private var job: Job? = null

    private val _pixabayPictures by lazy { MutableLiveData<Resource<PictureResponse>>() }
    val pixabayPictures: LiveData<Resource<PictureResponse>> get() = _pixabayPictures

    fun searchPictureJob(str: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            searchPicture(str)
        }
    }

    private fun searchPicture(searchStr: String){
        if(searchStr.isEmpty()) {
            return
        }

        _pixabayPictures.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchPicture(searchStr)
            _pixabayPictures.postValue(response)
        }
    }

    fun onPictureClick(url: String) = viewModelScope.launch {
        repository.insertPicture(Picture(url, false))
        addPictureEventChannel.send(AddPictureEvent.NavigateBackToTitle)
    }


    sealed class AddPictureEvent{
        object NavigateBackToTitle : AddPictureEvent()
    }

}