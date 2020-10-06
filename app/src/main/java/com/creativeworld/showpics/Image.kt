package com.creativeworld.showpics

import android.content.ContentUris
import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class Image (
    val uri: Uri,
    val name: String,
    val date: String
){
//Data
}