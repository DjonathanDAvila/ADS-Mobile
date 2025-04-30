package com.example.trabalho_final.entity.enums

enum class TravelType {
    NEGOCIO,
    LAZER
}

fun TravelType.toDisplayName(): String {
    return when (this) {
        TravelType.NEGOCIO -> "Negócio"
        TravelType.LAZER -> "Lazer"
    }
}