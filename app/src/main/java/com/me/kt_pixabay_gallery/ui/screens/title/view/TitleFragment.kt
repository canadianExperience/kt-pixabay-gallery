package com.me.kt_pixabay_gallery.ui.screens.title.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.databinding.FragmentTitleBinding
import com.me.kt_pixabay_gallery.ui.screens.title.viewmodel.TitleViewModel
import com.me.kt_pixabay_gallery.util.Util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TitleFragment : Fragment(R.layout.fragment_title) {

    private val viewModel: TitleViewModel by viewModels()
    private var fragmentBinding: FragmentTitleBinding? = null
    private var menuItem: MenuItem? = null
    private lateinit var titleRecyclerViewAdapter: TitleRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleRecyclerViewAdapter = TitleRecyclerViewAdapter(viewModel.glideRequestManager)

        val binding = FragmentTitleBinding.bind(view)
        fragmentBinding = binding

        binding.add.setOnClickListener {
           viewModel.onAddBtnClick()
        }

        binding.titleRecyclerView.apply {
            adapter = titleRecyclerViewAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }

        titleRecyclerViewAdapter.setOnItemClickListener {
            viewModel.onPictureClick(it.first, it.second)
        }

        titleRecyclerViewAdapter.apply {
            setOnFavoriteClickListener {viewModel.updateFavorite(it.first, it.second)}
        }

        viewModel.pictures.observe(viewLifecycleOwner){
            titleRecyclerViewAdapter.pictures = it?: listOf()
            menuItem?.isEnabled = !it.isNullOrEmpty()
        }

        viewModel.isShowFavoritesLiveData.observe(viewLifecycleOwner){
            val icon = getMenuIcon(it)
            menuItem?.setIcon(icon)
        }

        getTitleEvents()

        setHasOptionsMenu(true)
    }

    private fun getTitleEvents() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        viewModel.titleEvent.collect { event->
            when(event){
                TitleViewModel.TitleEvent.NavigateToAddPictureFragment ->{
                    goToAddPictureFragment()
                }
                is TitleViewModel.TitleEvent.NavigateToZoomPictureFragment -> {
                    goToZoomPictureFragment(event.url, event.id)
                }
            }.exhaustive
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_title, menu)

        menuItem = menu.findItem(R.id.action_favorites)
        val icon = getMenuIcon(viewModel.isShowFavoritesLiveData.value?:false)
        menuItem?.setIcon(icon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_favorites -> {
                viewModel.setIsShowFavorites()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getMenuIcon(isFavorite: Boolean) =
        if(isFavorite) R.drawable.ic_favorite_filled_control_normal_24dp else
            R.drawable.ic_favorite_control_normal_24dp

    private fun goToAddPictureFragment(){
        val action = TitleFragmentDirections.titleFragmentToAddPictureFragment()
        findNavController().navigate(action)
    }

    private fun goToZoomPictureFragment(url: String, id: Int){
        val action = TitleFragmentDirections.titleFragmentToZoomPictureFragment(url, id)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}