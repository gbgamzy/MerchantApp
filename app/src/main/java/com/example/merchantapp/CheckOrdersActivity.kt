package com.example.merchantapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ajubamerchant.classes.Network
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.adapters.CheckOrdersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_check_orders.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class CheckOrdersActivity : AppCompatActivity() {
    @Inject
    lateinit var api: Network
    var date:Date =Date()
    var adapter:CheckOrdersAdapter ?=null
    var list:ArrayList<Order> =ArrayList()
    val formatter= SimpleDateFormat("dd MM yyyy HH.mm")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_orders)
        val intent=getIntent()
        buttonBack.visibility= View.GONE
        btNext.visibility= View.VISIBLE
        recyclerViewOrderList.visibility= View.GONE
        calendarViewOrderList.visibility= View.VISIBLE


        val phone = intent.extras?.getInt("phone")
        var p:List<Order> ?=null
        CoroutineScope(Dispatchers.IO).launch {
            p = phone?.let { api.getRiderOrders(it).body() }
            list.clear()
            p?.let { list.addAll(it) }
            Log.d("Size",list.toString())
        }



        recyclerViewOrderList.layoutManager=LinearLayoutManager(this)
        adapter= CheckOrdersAdapter(list,this)
        recyclerViewOrderList.adapter=adapter



        btNext.setOnClickListener {
            buttonBack.visibility= View.VISIBLE
            btNext.visibility= View.GONE
            recyclerViewOrderList.visibility= View.VISIBLE
            calendarViewOrderList.visibility= View.GONE
            list.clear()
            p?.forEach {

                val d= formatter.parse(it.date)
                if(d.date==date.date && d.month==date.month && d.year==date.year ){
                    list.add(it)
                }
            }
            adapter?.notifyDataSetChanged()


        }
        buttonBack.setOnClickListener{
            buttonBack.visibility= View.GONE
            btNext.visibility= View.VISIBLE
            recyclerViewOrderList.visibility= View.GONE
            calendarViewOrderList.visibility= View.VISIBLE

        }

        calendarViewOrderList.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date.date=dayOfMonth
            date.month=month
            Log.d("date",date.toString())
            date.year=year-1900
        }


//        val refreshedToken = FirebaseInstanceId.getInstance().token
//        Log.d("TAG", "Refreshed token: $refreshedToken")






    }

}