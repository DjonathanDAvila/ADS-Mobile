package com.example.trabalho_final.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trabalho_final.entity.enums.TravelType
import java.time.LocalDateTime

@Entity
data class Travel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destination: String,
    val type: TravelType,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val budget: Double
)