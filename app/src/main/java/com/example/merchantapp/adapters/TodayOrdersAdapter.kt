package com.example.merchantapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat

class TodayOrdersAdapter(
    var list:ArrayList<Order>, var riders:List<DeliveryBoy>, val context: Context
) :

    RecyclerView.Adapter<TodayOrdersAdapter.ViewHolder>() {
    var name:String=""
    val formatter= SimpleDateFormat("dd MM yyyy HH.mm")
    val format= SimpleDateFormat("HH:mm")





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ticket_view_pager, parent, false)

        return ViewHolder(itemView)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var image: ImageView =view.findViewById(R.id.imageView5)
        var contents: TextView =view.findViewById(R.id.textView2)
        var flag:ImageView=view.findViewById(R.id.imageView5)
        var price: TextView =view.findViewById(R.id.tvFoodPrice)
        var name: TextView =view.findViewById(R.id.tvName)
        var date: TextView =view.findViewById(R.id.tvDate)

        var rider: TextView =view.findViewById(R.id.tvRider)
        var address: TextView =view.findViewById(R.id.tvAddress)
        var layout: LinearLayout =view.findViewById(R.id.llOrder)
        var layout1: LinearLayout =view.findViewById(R.id.llOrder1)
        var fabCustomer: FloatingActionButton = view.findViewById(R.id.fabCustomer)
        var fabRider: FloatingActionButton = view.findViewById(R.id.fabRider)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text= "₹ "+list[position].price.toString()
        val address=list[position].houseName+", "+list[position].streetAddress
        holder.address.text=address
        val i=formatter.parse(list[position].date)
        var date=format.format(i)
        holder.date.text=date
        val ri= riders.find {
            it.deliveryBoyPhone==list[position].deliveryBoy
        }

        if(list[position].status=="C")
        holder.flag.setBackgroundResource(R.drawable.order_status_accepted)
        else if(list[position].status=="D")
            holder.flag.setBackgroundResource(R.drawable.order_status_rejected)
        else if(list[position].status=="B")
            holder.flag.setBackgroundResource(R.drawable.order_status_dispatched)
        else if(list[position].status=="A2")
            holder.flag.setBackgroundResource(R.drawable.order_status_processing)
        else if(list[position].status=="A")
            holder.flag.setBackgroundResource(R.drawable.order_status_pending)


        holder.contents.text=list[position].contents
        holder.name.text=list[position].name
        holder.rider.text=ri?.deliveryBoyName


        holder.fabCustomer.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + list[position].phone)
            context.startActivity(dialIntent)


        }

        holder.fabRider.setOnClickListener {

                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + list[position].deliveryBoy)
                context.startActivity(dialIntent)
            }


    }


    override fun getItemCount(): Int {
        return list.size
    }


}
