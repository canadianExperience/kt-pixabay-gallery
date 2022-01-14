package com.me.kt_pixabay_gallery.ui.screens.addpicture.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.me.kt_pixabay_gallery.R
import javax.inject.Inject

class AddPictureRecyclerViewAdapter (
    private val glide: RequestManager
): RecyclerView.Adapter<AddPictureRecyclerViewAdapter.AddPictureViewHolder>(){

    class AddPictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var onItemClickListener: ((String) -> Unit) ?= null

    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffUtil)

    var urls: List<String>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPictureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_picture_item, parent, false)
        return AddPictureViewHolder(view)
    }

    fun setOnItemClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: AddPictureViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.pixabayPictureImageView)
        val url = urls[position]
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
        return urls.size
    }


}