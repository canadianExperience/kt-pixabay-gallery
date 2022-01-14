package com.me.kt_pixabay_gallery.ui.screens.title.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.roomdb.Picture
import javax.inject.Inject

class TitleRecyclerViewAdapter(
    private val glide: RequestManager
): RecyclerView.Adapter<TitleRecyclerViewAdapter.PictureViewHolder>(){

    class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var onItemClickListener: ((Pair<String, Int>) -> Unit) ?= null
    private var onFavoriteClickListener: ((Pair<Boolean,Int>) -> Unit) ?= null

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

    fun setOnItemClickListener(listener : (Pair<String, Int>) -> Unit){
        onItemClickListener = listener
    }

    fun setOnFavoriteClickListener(listener : (Pair<Boolean,Int>) -> Unit){
        onFavoriteClickListener = listener
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.pictureImageView)
        val checkBox = holder.itemView.findViewById<CheckBox>(R.id.favoriteCheckBox)
        val picture = pictures[position]
        holder.itemView.apply {
            glide.load(picture.url).into(imageView)
            this.setOnClickListener {
                onItemClickListener?.let { pair->
                    picture.id?.let {
                        pair(Pair(picture.url, it))
                    }

                }
            }
        }

        checkBox.setOnClickListener {
            onFavoriteClickListener?.let { pair ->
                val id = picture.id
                id?.let {
                    pair(Pair(!picture.isFavorite,it))
                }

            }
        }

        checkBox.isChecked = picture.isFavorite
    }

    override fun getItemCount(): Int {
        return pictures.size
    }


}