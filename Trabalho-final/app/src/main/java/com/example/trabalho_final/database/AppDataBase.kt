package com.example.trabalho_final.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.trabalho_final.dao.SuggestionDAO
import com.example.trabalho_final.dao.TravelDao
import com.example.trabalho_final.dao.UserDao
import com.example.trabalho_final.entity.Suggestion
import com.example.trabalho_final.entity.Travel
import com.example.trabalho_final.entity.User

@Database(entities = [User::class, Travel::class, Suggestion::class], version = 4, exportSchema = false)
@TypeConverters(DataBaseTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun travelDao(): TravelDao
    abstract fun suggestionDao(): SuggestionDAO

    companion object {
        @Volatile
        private var Instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDataBase::class.java, "user_database")
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_2_3)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE `user` " +
                    " ADD COLUMN inserDate INTEGER NOT NULL DEFAULT 0"
        )
    }
}