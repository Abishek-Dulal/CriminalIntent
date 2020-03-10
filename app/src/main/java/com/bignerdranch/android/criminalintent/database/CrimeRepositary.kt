package com.bignerdranch.android.criminalintent.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.criminalintent.Crime
import java.io.File
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"


class CrimeRepositary private constructor(context: Context){

    private val database :CrimeDataBase =Room.databaseBuilder(
                                              context.applicationContext,
                                              CrimeDataBase::class.java,
                                               DATABASE_NAME).addMigrations(migration_1_2).build()

    private val filesDir= context.applicationContext.filesDir


    private val crimeDao =database.crimeDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCrimes() :LiveData<List<Crime>> =crimeDao.getCrimes()

    fun getCrime(id:UUID):LiveData<Crime?> = crimeDao.getCrime(id)

    fun updateCrime(crime: Crime) {
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    fun addCrime(crime: Crime) {
        executor.execute {
            crimeDao.addCrime(crime)
        }
    }

    companion object{
        private var INSTANCE :CrimeRepositary?=null

        fun initialise(context: Context){
            if(INSTANCE==null){
                INSTANCE=CrimeRepositary(context)
            }
        }

        fun get(): CrimeRepositary {
            return INSTANCE ?: throw  IllegalStateException("CrimeRepository must be initialized")
        }

    }

    fun getPhotoFile(crime: Crime):File=File(filesDir,crime.photoFileName)

}