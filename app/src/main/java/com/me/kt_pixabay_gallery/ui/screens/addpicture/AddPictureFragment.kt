package com.me.kt_pixabay_gallery.ui.screens.addpicture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.databinding.FragmentAddPictureBinding

class AddPictureFragment: Fragment(R.layout.fragment_add_picture) {

    private var fragmentBinding: FragmentAddPictureBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddPictureBinding.bind(view)
        fragmentBinding = binding
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}