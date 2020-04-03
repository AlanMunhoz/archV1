package com.example.archv1.data.room

import androidx.room.TypeConverter

class AlbumTypeConverter {

    @TypeConverter
    fun stringListToString(lstString: List<String>) = lstString.joinToString(separator = "|")

    @TypeConverter
    fun stringToStringList(str: String) = str.split("|")
}