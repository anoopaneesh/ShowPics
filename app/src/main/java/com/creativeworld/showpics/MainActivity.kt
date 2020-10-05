package com.creativeworld.showpics

import android.Manifest
import android.content.Context
import android.content.Intent
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
class MainActivity : AppCompatActivity() ,ImageClickListener {
    val REQUEST_CODE = 1
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        showPics()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) getPics()
            else showRationale()
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onImageClick(item: Image) {
        val intent = Intent(this,ImageViewActivity::class.java)
        intent.putExtra("uri",item.uri.toString())
        startActivity(intent)
    }
    @RequiresApi(android.os.Build.VERSION_CODES.M)
    fun showPics() {
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED){
            getPics()
        }else{
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                showRationale()
            }
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
        }
    }
    fun showRationale(){
        Toast.makeText(this,"Please provide storage permission",Toast.LENGTH_SHORT)
    }
    fun getPics(){
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

}
