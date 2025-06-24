package com.example.trabalho_final.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trabalho_final.entity.Suggestion

@Dao
interface SuggestionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(suggestion: Suggestion)

    @Query("SELECT * FROM suggestion WHERE travelId = :travelId LIMIT 1")
    suspend fun getByTravelId(travelId: Int): Suggestion?
}