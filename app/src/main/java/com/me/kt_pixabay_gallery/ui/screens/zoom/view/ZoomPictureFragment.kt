package com.me.kt_pixabay_gallery.ui.screens.zoom.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.databinding.FragmentZoomPictureBinding
import com.me.kt_pixabay_gallery.ui.screens.zoom.viewmodel.ZoomPictureViewModel
import com.me.kt_pixabay_gallery.util.Util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ZoomPictureFragment: Fragment(R.layout.fragment_zoom_picture) {
    private val viewModel: ZoomPictureViewModel by viewModels()
    private var fragmentBinding: FragmentZoomPictureBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentZoomPictureBinding.bind(view)
        fragmentBinding = binding

        viewModel.glideRequestManager.load(viewModel.url).into(binding.zoomImageView)

        getZoomEvents()

        setHasOptionsMenu(true)
    }

    private fun getZoomEvents() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        viewModel.zoomEvent.collect { event->
            when(event){
                is ZoomPictureViewModel.ZoomEvent.NavigateBackToTitleFragment -> {
                    setFragmentResult(
                        "message_result",
                        bundleOf("message_result" to event.message)
                    )
                    findNavController().popBackStack()
                }
            }.exhaustive
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_zoom, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_delete -> {
                viewModel.onDeletePictureClick()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}