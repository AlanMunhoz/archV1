package com.example.archv1.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.archv1.domain.model.Album
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlbumPrefs(private val mContext: Context) {

    companion object AccessKey {
        private const val FILE_ALBUM_LIST = "file_album_list"
        private const val KEY_ALBUM_LIST = "key_album_list"
    }

    fun saveAlbumList(lstAlbum: ArrayList<Album>) {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences(FILE_ALBUM_LIST, Context.MODE_PRIVATE)
        val prefsEditor = sharedPreferences.edit()
        val json = Gson().toJson(lstAlbum)
        prefsEditor.putString(KEY_ALBUM_LIST, json)
        prefsEditor.apply()
    }

    private fun restoreAlbumList(): ArrayList<Album>? {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences(FILE_ALBUM_LIST, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(KEY_ALBUM_LIST, "")
        val listType = object : TypeToken<ArrayList<Album>>() {}.type
        return Gson().fromJson<ArrayList<Album>>(json, listType)
    }

    fun insert(album: Album) {
//        getContext()?.let { context ->
        restoreAlbumList()?.let { list ->
            if (list.firstOrNull { it.id == album.id } == null) {
                list.add(album)
                saveAlbumList(list)
            }
        } ?: run {
            saveAlbumList(arrayListOf(album))
        }

    }

    fun getAlbum(albumId: Int): Album? {
//        return getContext()?.let { context ->
        return restoreAlbumList()?.let { list ->
            list.firstOrNull { it.id == albumId }
        }
    }

    fun getAll() = restoreAlbumList()

}