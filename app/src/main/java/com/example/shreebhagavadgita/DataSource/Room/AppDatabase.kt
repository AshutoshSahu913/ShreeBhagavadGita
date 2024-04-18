package com.example.shreebhagavadgita.DataSource.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SavedChapterEntity::class], version = 1, exportSchema = false)

@TypeConverters(Type_Converters::class)
abstract class AppDatabase : RoomDatabase() {


    abstract fun savedChapterDao(): SaveChapterDao

    companion object {
        //create instance of database
        val Instance: AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase {
            val tempInstance = Instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomDB = Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
                    //jab bhi schema m change karte h toh purana data delete kar deta h
                    .fallbackToDestructiveMigration().build()
                return roomDB
            }
        }
    }
}