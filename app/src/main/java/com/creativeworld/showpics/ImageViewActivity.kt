package com.creativeworld.showpics

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        val imageView = findViewById<ImageView>(R.id.imageView)
         val uriString = intent.getStringExtra("uri")
        val uri = Uri.parse(uriString)
        Glide.with(this)
            .load(uri)
            .centerCrop()
            .into(imageView)
    }
}