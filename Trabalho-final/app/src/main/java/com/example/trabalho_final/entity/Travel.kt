package com.example.trabalho_final.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trabalho_final.entity.enums.TravelType

@Entity
data class Travel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destination: String,
    val type: TravelType,
    val startDate: Long,
    val endDate: Long,
    val budget: Double
)