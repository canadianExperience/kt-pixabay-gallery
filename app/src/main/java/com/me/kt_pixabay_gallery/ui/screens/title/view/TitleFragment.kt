package com.me.kt_pixabay_gallery.ui.screens.title.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.databinding.FragmentTitleBinding
import com.me.kt_pixabay_gallery.ui.screens.addpicture.viewmodel.AddPictureViewModel
import com.me.kt_pixabay_gallery.ui.screens.title.viewmodel.TitleViewModel
import javax.inject.Inject

class TitleFragment @Inject constructor(
    private val titleRecyclerViewAdapter: TitleRecyclerViewAdapter
) : Fragment(R.layout.fragment_title) {

    private lateinit var viewModel: TitleViewModel
    private var fragmentBinding: FragmentTitleBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TitleViewModel::class.java]

        val binding = FragmentTitleBinding.bind(view)
        fragmentBinding = binding

        binding.add.setOnClickListener {
            goToAddPictureFragment()
        }

        binding.titleRecyclerView.apply {
            adapter = titleRecyclerViewAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }

        titleRecyclerViewAdapter.setOnItemClickListener {
          //  Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.pictures.observe(viewLifecycleOwner){
            titleRecyclerViewAdapter.pictures = it?: listOf()
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