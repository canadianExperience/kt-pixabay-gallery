package com.me.kt_pixabay_gallery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.me.kt_pixabay_gallery.ui.screens.addpicture.view.AddPictureFragment
import com.me.kt_pixabay_gallery.ui.screens.addpicture.view.AddPictureRecyclerViewAdapter
import javax.inject.Inject

class PictureFragmentFactory @Inject constructor(
    private val addPictureRecyclerViewAdapter: AddPictureRecyclerViewAdapter,
    private val glide: RequestManager
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            AddPictureFragment::class.java.name -> AddPictureFragment(addPictureRecyclerViewAdapter)


            else ->  super.instantiate(classLoader, className)
        }


    }
}