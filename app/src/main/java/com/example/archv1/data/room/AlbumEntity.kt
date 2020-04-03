package com.example.archv1.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albumTable")
data class AlbumEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "userId") var userId: Int,
    @ColumnInfo(name = "musics") var lstMusics: List<String>
)