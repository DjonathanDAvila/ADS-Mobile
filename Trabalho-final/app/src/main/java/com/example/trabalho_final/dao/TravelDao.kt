package com.example.trabalho_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.trabalho_final.entity.Travel

@Dao
interface TravelDao {
    @Insert
    suspend fun insertTravel(travel: Travel)

    @Update
    suspend fun updateTravel(travel: Travel)

    @Query("SELECT * FROM travel ORDER BY startDate ASC")
    suspend fun getAllTravels(): List<Travel>

    @Delete
    suspend fun deleteTravel(travel: Travel)

    @Query("SELECT * FROM travel WHERE id = :id LIMIT 1")
    suspend fun getTravelById(id: Int): Travel?
}