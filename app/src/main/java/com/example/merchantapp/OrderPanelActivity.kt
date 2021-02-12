package com.example.merchantapp

import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.ajubamerchant.classes.Network
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.classes.HomeDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_order_panel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@AndroidEntryPoint
class OrderPanelActivity  : AppCompatActivity() {
    @Inject
    lateinit var api: Network
    @Inject
    lateinit var db:HomeDao
    var list:ArrayList<Order> =ArrayList()
    var order: Order?= null
    var s=""
    var sc=0
    var sp=0
    var fc=0
    var fp=0
    var id=""
    var phone=""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_panel)
        val extras: Bundle? =this.intent.extras
        id = intent.getStringExtra("id").toString()
        phone= intent.getStringExtra("phone").toString()
        CoroutineScope(Dispatchers.Main).launch {
            try{
                api.getDetails(phone).body()?.let { list.addAll(it) }
                order = api.getOrder(id).body()

            }
            catch(err:Exception){

            }


        }

        order?.contents?.forEach {
            s+= it.name+"x"+it.quantity+" "
        }
        tvaddress.text= order?.address?.address
        tvcon.text=s
        tvphone.text= order?.customerPhone
        tvPrice.text=order?.price.toString()
        list.forEach {
            if(it.status=="C"){
                sc++
                sp+=it.price

            }
            else if(it.status=="D"){
                fc++
                fp+=it.price

            }

        }
        tvfailedcount.text=fc.toString()
        tvfailedprice.text=fp.toString()
        tvsuccesscount.text=sc.toString()
        tvsuccessprice.text=sp.toString()

        btAccept.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                try{ val v = api.processOrders(id) }
                catch(err:Exception){
                    Log.d("vmvmvmv",err.toString())
                }
                finish()




            }
            



        }
        btReject.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch{

                Log.d("idaaaaa",id)
                if (id != null) {
                    val o=db.getOrder(id)
                    Log.d("idaaaaa",o.toString())
                    val m=api.rejectOrders(id,o)
                    finish()


                }

            }
        }




    }
    suspend fun accept(){

        Log.d("oder",".toString()")

    }
}