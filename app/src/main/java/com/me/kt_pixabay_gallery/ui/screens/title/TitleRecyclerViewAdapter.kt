package com.me.kt_pixabay_gallery.ui.screens.title

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.roomdb.Picture
import javax.inject.Inject

class TitleRecyclerViewAdapter @Inject constructor(
    private val glide: RequestManager
): RecyclerView.Adapter<TitleRecyclerViewAdapter.PictureViewHolder>(){

    class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var onItemClickListener: ((String) -> Unit) ?= null

    private val diffUtil = object : DiffUtil.ItemCallback<Picture>(){
        override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffUtil)

    var pictures: List<Picture>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.picture_item, parent, false)
        return PictureViewHolder(view)
    }

    fun setOnItemClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.pictureImageView)
        val url = pictures[position].url
        holder.itemView.apply {
            glide.load(url).into(imageView)
            this.setOnClickListener {
                onItemClickListener?.let {
                        it(url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return pictures.size
    }


}