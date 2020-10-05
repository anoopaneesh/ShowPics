package com.creativeworld.showpics


import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class ImageViewModel(val context: Context) : ViewModel() {
    private var _imageList = MutableLiveData<List<Image>>()
    val scope = CoroutineScope(Dispatchers.Main)
    val imageList: LiveData<List<Image>>
        get() = _imageList
    init {
        scope.launch {
            _imageList.value = queryImages()
        }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }

    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.DATE_MODIFIED
    )

    // Display videos in alphabetical order based on their display name.
    val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
    private suspend fun queryImages(): List<Image> {
        val images = mutableListOf<Image>()
        withContext(Dispatchers.IO){
        val query = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null, null,
            sortOrder
        )
        query?.use { cursor: Cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)


            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val date = cursor.getInt(dateColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                images += Image(contentUri, name, date.toString())
            }
        }
    }

        return images

    }
}