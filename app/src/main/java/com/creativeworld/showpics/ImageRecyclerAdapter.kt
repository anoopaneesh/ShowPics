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

class ImageRecyclerAdapter(val onImageClick : OnImageClickListener) :  ListAdapter<Image,ImageViewHolder>(ImageDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}
class ImageViewHolder private constructor(val binding : ImageItemBinding,val onImageClick: OnImageClickListener) : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener{
    fun bind(item : Image){
        Glide.with(binding.imageItemView)
            .load(item.uri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(binding.imageItemView)

    }
    companion object{
        fun from(parent : ViewGroup) : ImageViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding : ImageItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.image_item,parent,false)
            return ImageViewHolder(binding)
        }
    }

    override fun onClick(v: View?) {
        onImageClick.onImageClicked(adapterPosition)
    }
}
interface OnImageClickListener{
    fun onImageClicked(item : Image)
}

class ImageDiffCallback : DiffUtil.ItemCallback<Image>(){
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}