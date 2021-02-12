package com.example.merchantapp.ui.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.ajubamerchant.classes.Network
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.classes.HomeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class HomeViewModel @ViewModelInject constructor(val api:Network,val db:HomeDao): ViewModel() {

    val pending: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    val dispatched: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    val processing: MutableLiveData<ArrayList<Order>> = MutableLiveData()

    suspend fun refreshHome(){
        db.clearOrder()
        val list: ArrayList<Order> =ArrayList()
        val list1: ArrayList<Order> =ArrayList()
        val list2: ArrayList<Order> =ArrayList()
        api.getPendingOrders().body()?.let { it ->
            list.addAll(it)
        it.forEach { m->
            db.addOrder(m)
        }}
        api.getProcessingOrders().body()?.let { list1.addAll(it) }
        api.getDispatchedOrders().body()?.let { list2.addAll(it) }

        list1.forEach {
            db.addOrder(it!!)
        }
        list2.forEach {
            db.addOrder(it)
        }


        withContext(Dispatchers.Main){
            pending.value = list
            processing.value = list1
            dispatched.value = list2
        }
        Log.d("vm", list1.toString())

    }

    suspend fun getDispatchedOrders(){
        var list2:ArrayList<Order> =ArrayList()
        api.getDispatchedOrders().body()?.let { list2.addAll(it) }
        dispatched?.value=list2
    }
    suspend fun getRiders(): List<DeliveryBoy>? {
        return api.getRiders().body()
    }








}