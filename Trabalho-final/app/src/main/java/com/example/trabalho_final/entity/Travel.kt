package com.example.trabalho_final.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Travel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destination: String,
    val type: String, // "Negócio" ou "Lazer"
    val startDate: Long,
    val endDate: Long,
    val budget: Double
)