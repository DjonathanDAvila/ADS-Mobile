package com.example.trabalho_final.database

import androidx.room.TypeConverter
import com.example.trabalho_final.entity.enums.TravelType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DataBaseTypeConverter {
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): Long? {
        return value?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime? {
        return value?.let {
            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())?.toLocalDateTime()
        }
    }

    @TypeConverter
    fun fromTravelType(type: TravelType): String = type.name

    @TypeConverter
    fun toTravelType(value: String): TravelType = TravelType.valueOf(value.uppercase())

}