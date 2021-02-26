package com.example.merchantapp.ui.riders

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ajubamerchant.classes.FoodMenu
import com.example.ajubamerchant.classes.Network
import com.example.ajubamerchant.classes.Rider
import com.example.merchantapp.classes.HomeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RiderViewModel @ViewModelInject constructor(val api: Network, application: Application) : AndroidViewModel(application) {
    val riders:MutableLiveData<ArrayList<Rider>> = MutableLiveData()
    private val context = getApplication<Application>().applicationContext
    suspend fun reload(){
        try{
            var list:ArrayList<Rider> =ArrayList()
            val p=api.getRidersList().body()
            if (p != null) {
                list.addAll(p)
            }
            withContext(Dispatchers.Main){ riders.value = list }



        }
        catch(err:Exception){
            Log.d("vmrider",err.toString())
        }

    }
    suspend fun addRider(r:Rider){
        try{ api.uploadRider(r)

            reload()}
        catch(err:Exception){
            Log.d("vmError",err.toString())
        }
    }
    suspend fun deleteRider(r:String){
        try{ api.deleteRider(r)
            reload()}
        catch(err:Exception){
            Log.d("vmError",err.toString())
        }

    }



}