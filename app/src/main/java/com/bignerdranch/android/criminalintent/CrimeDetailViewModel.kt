package com.bignerdranch.android.criminalintent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.criminalintent.database.CrimeRepositary
import java.io.File
import java.util.*

class CrimeDetailViewModel:ViewModel(){
    private val crimeRepositary:CrimeRepositary = CrimeRepositary.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()
    var crimeLiveData: LiveData<Crime?> = Transformations.switchMap(crimeIdLiveData){
        crimeid -> crimeRepositary.getCrime(crimeid)
    }

    fun loadCrime(crimeId: UUID) {
        crimeIdLiveData.value = crimeId
    }


    fun saveCrime(crime: Crime) {
        crimeRepositary.updateCrime(crime)
    }

    fun getPhotoFile(crime: Crime): File {
        return crimeRepositary.getPhotoFile(crime)
    }
}