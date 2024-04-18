package com.example.shreebhagavadgita.DataSource.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlin.concurrent.Volatile

@Database(
    entities = [SavedChapterEntity::class, SavedVersesEntity::class],
    version = 2/*version change after create new table */,
    exportSchema = false
)

@TypeConverters(Type_Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun savedChapterDao(): SaveChapterDao
    abstract fun savedVersesDao(): SavedVersesDao

    companion object {
        //create instance of database
        @Volatile
        var Instance: AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase {
            val tempInstance = Instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomDB = Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
                    //jab bhi schema m change karte h toh ye purana data delete kar deta h
                    .fallbackToDestructiveMigration().build()
                return roomDB
            }
        }
    }
}