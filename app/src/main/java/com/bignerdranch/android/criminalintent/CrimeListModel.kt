package layout

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.criminalintent.Crime
import com.bignerdranch.android.criminalintent.database.CrimeRepositary

class CrimeListModel :ViewModel(){
    private val crimeRepositary=CrimeRepositary.get()
    val crimelistLiveData = crimeRepositary.getCrimes()

    fun addCrime(crime: Crime){
        crimeRepositary.addCrime(crime)
    }

}