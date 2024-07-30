package com.example.mesimah.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Mesimah::class], version=1, exportSchema = false)
abstract class MesimahDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object {
        @Volatile
        private var INSTANCE: MesimahDatabase? = null

        fun getInstance(context: Context): MesimahDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MesimahDatabase::class.java,
                        "mesimah_database",
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}