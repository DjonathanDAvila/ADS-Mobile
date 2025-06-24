package com.example.trabalho_final.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Suggestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val travelId: Int,
    val text: String,
    val modified: Boolean = false
)
