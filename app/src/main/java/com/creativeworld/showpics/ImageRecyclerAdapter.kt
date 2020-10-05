package com.creativeworld.showpics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creativeworld.showpics.databinding.ImageItemBinding

class ImageRecyclerAdapter(val imageClickListener: ImageClickListener) :  ListAdapter<Image,ImageViewHolder>(ImageDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,imageClickListener)
    }


}
class ImageViewHolder private constructor(val binding : ImageItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : Image,imageClickListener: ImageClickListener) {
        Glide.with(binding.imageItemView)
            .load(item.uri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(binding.imageItemView)
        binding.imageItemView.setOnClickListener {
            imageClickListener.onImageClick(item)
        }
    }

    companion object{
        fun from(parent : ViewGroup) : ImageViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding : ImageItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.image_item,parent,false)
            return ImageViewHolder(binding)
        }
    }

}

class ImageDiffCallback : DiffUtil.ItemCallback<Image>(){
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}
interface ImageClickListener{
    fun onImageClick(item: Image)
}