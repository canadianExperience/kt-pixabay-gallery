package com.me.kt_pixabay_gallery.ui.screens.title

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.databinding.FragmentTitleBinding

class TitleFragment: Fragment(R.layout.fragment_title) {

    private var fragmentBinding: FragmentTitleBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTitleBinding.bind(view)
        fragmentBinding = binding

        binding.add.setOnClickListener {
            goToAddPictureFragment()
        }
    }

    private fun goToAddPictureFragment(){
        val action = TitleFragmentDirections.titleFragmentToAddPictureFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}