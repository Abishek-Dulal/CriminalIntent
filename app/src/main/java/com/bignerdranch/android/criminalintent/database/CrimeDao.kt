package com.bignerdranch.android.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.criminalintent.Crime
import java.util.*

@Dao
interface CrimeDao {

    @Query("select * from crime")
    fun getCrimes():LiveData<List<Crime>>

    @Query("select * from Crime where id=(:uuid)")
    fun getCrime(uuid:UUID):LiveData<Crime?>

    @Update
    fun updateCrime(crime: Crime)

    @Insert
    fun addCrime(crime: Crime)
}