package com.example.trabalho_final.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val inserDate: LocalDateTime = LocalDateTime.now()
)