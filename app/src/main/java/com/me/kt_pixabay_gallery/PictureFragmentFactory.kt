package com.me.kt_pixabay_gallery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.me.kt_pixabay_gallery.ui.screens.addpicture.view.AddPictureFragment
import com.me.kt_pixabay_gallery.ui.screens.addpicture.view.AddPictureRecyclerViewAdapter
import com.me.kt_pixabay_gallery.ui.screens.title.view.TitleFragment
import com.me.kt_pixabay_gallery.ui.screens.title.view.TitleRecyclerViewAdapter
import javax.inject.Inject

class PictureFragmentFactory @Inject constructor(
    private val titleRecyclerViewAdapter: TitleRecyclerViewAdapter,
    private val addPictureRecyclerViewAdapter: AddPictureRecyclerViewAdapter,
    private val glide: RequestManager
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            TitleFragment::class.java.name -> TitleFragment(titleRecyclerViewAdapter)
            AddPictureFragment::class.java.name -> AddPictureFragment(addPictureRecyclerViewAdapter)


            else ->  super.instantiate(classLoader, className)
        }


    }
}