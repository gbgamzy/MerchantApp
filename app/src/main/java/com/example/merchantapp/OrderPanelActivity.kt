package com.example.merchantapp

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
    lateinit var db: HomeDao
    var list: ArrayList<Order> = ArrayList()
    var order: Order? = null

    var sc = 0
    var sp = 0
    var fc = 0
    var fp = 0
    var id = ""
    var phone = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_panel)
        val extras: Bundle? = this.intent.extras
        id = intent.getStringExtra("id").toString()
        phone = intent.getStringExtra("phone").toString()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                api.getDetails(phone).body()?.let {
                    list.addAll(it)

                }
                order = api.getOrder(id).body()
                Log.d("vmOrder Panel",order.toString())


                tvaddress.text = order?.streetAddress
                tvcon.text = order?.contents
                tvphone.text = phone
                tvPrice.text = order?.price.toString()
                list.forEach {
                    if (it.status == "C") {
                        sc++
                        sp += it.price!!

                    } else if (it.status == "D") {
                        fc++
                        fp += it.price!!

                    }

                }
                tvfailedcount.text = fc.toString()
                tvfailedprice.text = "Rs." + fp.toString()
                tvsuccesscount.text = sc.toString()
                tvsuccessprice.text = "Rs." + sp.toString()

            } catch (err: Exception) {
                Log.d("vm OrderPanel", err.toString())

            }


        }
        tvphone.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + phone)
            startActivity(dialIntent)
        }


        btAccept.setOnClickListener {

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Accept Order?")
            dialog.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val v = api.processOrders(id)
                        Log.d("vmMessage", v.body()!!.message)
                    } catch (err: Exception) {
                        Log.d("vmvmvmv", err.toString())
                    }
                    finish()
                }

            }
            dialog.setNegativeButton("No"){ _: DialogInterface, _: Int ->

            }
            dialog.create().show()


        }



        btReject.setOnClickListener{
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Reject Order?")
            dialog.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d("idaaaaa", id)
                    if (id != null) {
                        val o = db.getOrder(id)
                        Log.d("idaaaaa", o.toString())
                        val m = api.rejectOrders(id, o)
                        finish()


                    }

                }

            }
            dialog.setNegativeButton("No"){ _: DialogInterface, _: Int ->

            }
            dialog.create().show()

        }
    }



    suspend fun accept(){

        Log.d("oder",".toString()")

    }
}