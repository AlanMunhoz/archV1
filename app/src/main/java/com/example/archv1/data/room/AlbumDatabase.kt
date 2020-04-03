package com.example.archv1.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [AlbumEntity::class], version = 1, exportSchema = false)
@TypeConverters(AlbumTypeConverter::class)
abstract class AlbumDatabase : RoomDatabase() {

    abstract val albumDao: AlbumDao

    companion object {

        private const val DATABASE_NAME = "ALBUM_DB.db"

        fun albumProvider(context: Context): AlbumDatabase =
            Room.databaseBuilder(context, AlbumDatabase::class.java, DATABASE_NAME)
                //.addMigrations(MIGRATION_1_2)
                //.fallbackToDestructiveMigration()
                .build()

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //TODO: For an eventual change in the database table, increment database version and:
                // 1) put the migration code here adding this function in ".addMigrations".
                // 2) if the previous data in database don't need be saved, just add ".fallbackToDestructiveMigration"
            }
        }
    }
}

