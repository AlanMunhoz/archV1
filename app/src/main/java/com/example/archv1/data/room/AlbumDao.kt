package com.example.archv1.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(albumEntity: AlbumEntity)

    @Delete
    suspend fun delete(albumEntity: AlbumEntity)

    @Query("SELECT * FROM albumTable WHERE id = :albumId")
    suspend fun getAlbum(albumId: Int): AlbumEntity?

    @Query("SELECT * FROM albumTable")
    suspend fun getAll(): List<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setList(albumList: List<AlbumEntity>)
}
