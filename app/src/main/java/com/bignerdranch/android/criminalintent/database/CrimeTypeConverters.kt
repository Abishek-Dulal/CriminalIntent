package com.bignerdranch.android.criminalintent.database

import androidx.room.TypeConverter
import java.util.*

class CrimeTypeConverters{
    @TypeConverter
    fun fromDate(date:Date?):Long?{
        return date?.time
    }

    @TypeConverter
    fun toDate(epochTime:Long?):Date?{
      return  epochTime?.let {
           Date(it)
       }
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }


}