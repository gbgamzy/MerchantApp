package com.example.merchantapp.ui.riders

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.merchantapp.classes.Network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RiderViewModel @ViewModelInject constructor(val api: Network, application: Application) : AndroidViewModel(application) {
    val riders:MutableLiveData<ArrayList<DeliveryBoy>> = MutableLiveData()
    private val context = getApplication<Application>().applicationContext
    suspend fun reload(){
        try{
            var list:ArrayList<DeliveryBoy> =ArrayList()
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
    suspend fun addRider(r:DeliveryBoy){
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