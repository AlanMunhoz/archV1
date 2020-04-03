package com.example.archv1.data.room

import androidx.room.*

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
}