@file:Suppress("DEPRECATION")

package com.example.merchantapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.Order

import com.example.merchantapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat

class CheckOrdersAdapter(
    var list:ArrayList<Order>, val context: Context
) :

    RecyclerView.Adapter<CheckOrdersAdapter.ViewHolder>(){
    val formatter= SimpleDateFormat("dd MM yyyy HH.mm")
    val format= SimpleDateFormat("HH:mm")




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ticket_view_pager, parent, false)
        Log.d("riderOrder",list.toString())

        return ViewHolder(itemView)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var image: ImageView =view.findViewById(R.id.imageView5)

        var contents: TextView =view.findViewById(R.id.textView2)



        var price: TextView =view.findViewById(R.id.tvFoodPrice)
        var name: TextView =view.findViewById(R.id.tvName)
        var date: TextView =view.findViewById(R.id.tvDate)
        var address: TextView =view.findViewById(R.id.tvAddress)
        var layout: LinearLayout =view.findViewById(R.id.llOrder)
        var layout1: LinearLayout =view.findViewById(R.id.llOrder1)
        var fabCustomer:FloatingActionButton = view.findViewById(R.id.fabCustomer)
        var fabRider:FloatingActionButton = view.findViewById(R.id.fabRider)



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text= "₹ "+list[position].price.toString()
        val address=list[position].houseName+", "+list[position].streetAddress
        holder.address.text=address
        val i=formatter.parse(list[position].date)
        var date=format.format(i)
        holder.date.text=date
        holder.contents.text=list[position].contents
        holder.name.text=list[position].name



        if(list[position].status=="C"){
            holder.image.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.order_status_accepted))
        }
        else if(list[position].status=="B"){
            holder.image.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.order_status_processing))
        }
        else if(list[position].status=="D"){
                    holder.image.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.order_status_rejected))
        }


    }



    override fun getItemCount(): Int {
        return list.size
    }


}