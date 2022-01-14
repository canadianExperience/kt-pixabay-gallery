package com.me.kt_pixabay_gallery.ui.screens.addpicture.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.databinding.FragmentAddPictureBinding
import com.me.kt_pixabay_gallery.ui.screens.addpicture.viewmodel.AddPictureViewModel
import com.me.kt_pixabay_gallery.util.Status
import com.me.kt_pixabay_gallery.util.Util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
@AndroidEntryPoint
class AddPictureFragment : Fragment(R.layout.fragment_add_picture) {

    private var fragmentBinding: FragmentAddPictureBinding? = null
    private val viewModel: AddPictureViewModel by viewModels()
    private lateinit var addPictureRecyclerViewAdapter: AddPictureRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPictureRecyclerViewAdapter = AddPictureRecyclerViewAdapter(viewModel.glideRequestManager)

        val binding = FragmentAddPictureBinding.bind(view)
        fragmentBinding = binding

        binding.searchText.addTextChangedListener { viewModel.searchPictureJob(it.toString()) }

        binding.addPictureRecyclerView.apply {
            adapter = addPictureRecyclerViewAdapter
            layoutManager = GridLayoutManager(requireContext(),4)
        }

        addPictureRecyclerViewAdapter.setOnItemClickListener {
            viewModel.onPictureClick(it)
        }

        observePixabayPictures()

        getAddPictureEvents()
    }

    private fun observePixabayPictures(){
        viewModel.pixabayPictures.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    addPictureRecyclerViewAdapter.urls = urls ?: listOf()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message?:"Error", Toast.LENGTH_LONG).show()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }
                Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getAddPictureEvents(){
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.addPictureEvent.collect { event->
                when(event){
                    is AddPictureViewModel.AddPictureEvent.NavigateBackToTitle -> {
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}