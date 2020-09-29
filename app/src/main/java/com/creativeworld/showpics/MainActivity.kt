package com.creativeworld.showpics

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.DatabaseUtils
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Layout
import android.widget.GridLayout
import android.widget.GridView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creativeworld.showpics.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() , OnImageClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val manager = GridLayoutManager(this,3)
        binding.imageRecyclerView.layoutManager = manager
        val factory = ImageViewModelFactory(this)
        val viewModel = ViewModelProvider(this,factory).get(ImageViewModel::class.java)
        val adapter = ImageRecyclerAdapter(this)
        viewModel.imageList.observe(this, Observer {
            adapter.submitList(it)
        })
        binding.imageRecyclerView.adapter = adapter

    }

    override fun onImageClicked(item: Image) {
        Toast.makeText(this,item.uri.toString(),Toast.LENGTH_SHORT)
    }
}
