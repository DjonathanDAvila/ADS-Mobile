package com.example.trabalho_final.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trabalho_final.dao.UserDao
import com.example.trabalho_final.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDataBase::class.java, "user_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}