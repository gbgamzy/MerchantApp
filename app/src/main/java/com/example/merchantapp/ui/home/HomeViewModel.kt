package com.example.merchantapp.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.ajubamerchant.classes.Network
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.classes.HomeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class HomeViewModel @ViewModelInject constructor(val api:Network,val db:HomeDao): ViewModel() {

    val pending: MutableLiveData<ArrayList<Order>> = MutableLiveData()

    val processing: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    val toBeAccepted: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    var riders:ArrayList<DeliveryBoy> =ArrayList()

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


        list1.forEach {
            if(it.deliveryBoyPhone==null || it.deliveryBoyPhone!!.length==0){
                list2.add(it)
            }
        }


        getRiders()
        withContext(Dispatchers.Main){
            pending.value = list
            processing.value = list1
            toBeAccepted.value=list2

        }


    }
    suspend fun dispatch(_id: Int, o: Order){
        api.acceptOrder(_id,o)
    }


    suspend fun getRiders() {
        riders.clear()
        val b=api.getRiders().body()
        val l:ArrayList<DeliveryBoy> =ArrayList()
        if (b != null) {
            l.addAll(b)
        }
        riders=l






    }








}