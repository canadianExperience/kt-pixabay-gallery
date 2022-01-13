package com.me.kt_pixabay_gallery.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.databinding.FragmentZoomPictureBinding

class ZoomPictureFragment: Fragment(R.layout.fragment_zoom_picture) {

    private var fragmentBinding: FragmentZoomPictureBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentZoomPictureBinding.bind(view)
        fragmentBinding = binding
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}