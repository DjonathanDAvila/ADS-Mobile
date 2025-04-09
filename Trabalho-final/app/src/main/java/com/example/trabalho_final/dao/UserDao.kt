package com.example.trabalho_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.trabalho_final.entity.User

@Dao
interface UserDao {
    //suspend para que a função seja assincrona
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Upsert
    suspend fun upsert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("select * from User u where u.id = :id")
    suspend fun findById(id: Int): User?

    @Query("select * from user u order by u.fullName")
    suspend fun findAll(): List<User>
}