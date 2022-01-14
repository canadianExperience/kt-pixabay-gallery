package com.me.kt_pixabay_gallery.ui.screens.zoom.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.databinding.FragmentZoomPictureBinding
import com.me.kt_pixabay_gallery.ui.screens.zoom.viewmodel.ZoomPictureViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZoomPictureFragment: Fragment(R.layout.fragment_zoom_picture) {
    private val viewModel: ZoomPictureViewModel by viewModels()
    private var fragmentBinding: FragmentZoomPictureBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentZoomPictureBinding.bind(view)
        fragmentBinding = binding

        viewModel.glideRequestManager.load(viewModel.url).into(binding.zoomImageView)
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}