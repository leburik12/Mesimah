package com.example.mesimah.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Mesimah::class], version=2, exportSchema = false)
@TypeConverters(Converter::class)
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
                    ).addMigrations(MIGRATION_1_2).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        val MIGRATION_1_2 = object: Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE mesimah_table ADD COLUMN start_date INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE mesimah_table ADD COLUMN end_date INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}