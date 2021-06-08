package com.example.merchantapp.ui.home


import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.merchantapp.classes.Network
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.classes.HomeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class HomeViewModel @ViewModelInject constructor(val api: Network, val db:HomeDao): ViewModel() {

    val pending: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    lateinit var pref: SharedPreferences
    lateinit var edit: SharedPreferences.Editor

    val processing: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    val dispatched: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    var riders:ArrayList<DeliveryBoy> =ArrayList()
    var todayOrder:MutableLiveData<ArrayList<Order>> = MutableLiveData()
    val formatter= SimpleDateFormat("dd MM yyyy HH.mm")

    suspend fun refreshHome(phone:String,token:String){
        var token_length=api.getAdmin().body()?.registrationToken?.length
        if(token_length!=null)
        if(token_length <10){
            api.login(phone,token)
        }
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
        api.getDispatchedOrders().body()?.let{
            list2.addAll(it)
        }





        getRiders()
        withContext(Dispatchers.Main){
            pending.value = list
            processing.value = list1
            dispatched.value=list2

        }


    }
    suspend fun getTodayOrders(day:String,month:String,year:String){
        var list= ArrayList<Order>()
        var x=api.getTodayOrders(day,month,year)
        x.toString()?.let { Log.d("todayOrders,", it) }
        x.body()?.let { list.addAll(it) }
        todayOrder.value=list
    }
    suspend fun dispatch(_id: Int, o: Order){
try {
    api.acceptOrder(_id, o)
}     catch(err:Exception){
            Log.d("AcceptError",err.toString())
        }
    }
    suspend fun setRider(oid:Int,o:Order){
        Log.d("SetRider",api.setRider(oid,o).toString())


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