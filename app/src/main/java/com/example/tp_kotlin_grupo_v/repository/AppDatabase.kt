package com.example.tp_kotlin_grupo_v.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tp_kotlin_grupo_v.domain.UserDao
import com.example.tp_kotlin_grupo_v.domain.User

@Database(version=1,entities = [User::class] )
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "user_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}