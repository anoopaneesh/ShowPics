package com.creativeworld.showpics

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity.apply
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat.apply
import com.bumptech.glide.Glide
import com.creativeworld.showpics.databinding.ActivityMainBinding.inflate
import kotlinx.coroutines.flow.emptyFlow

class ImageViewActivity : AppCompatActivity() {
    private var uri : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val uriString = intent.getStringExtra("uri")
         uri = Uri.parse(uriString)
        Glide.with(this)
            .load(uri)
            .centerCrop()
            .into(imageView)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.image_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if(item.itemId == R.id.shareImage){
           // Toast.makeText(this,"Shared image",Toast.LENGTH_SHORT).show()
             shareImage()
        }
        return super.onOptionsItemSelected(item)
    }

    fun shareImage(){
        val shareIntent = Intent().apply{
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM , uri)
            type = "image/png"
        }
        startActivity(Intent.createChooser(shareIntent,"Share Via"))
    }
}