package com.example.merchantapp.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.OrderPanelActivity
import com.example.merchantapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat


@Suppress("DEPRECATION")
class MenuChildAdapter(
    var list:ArrayList<Order>, val adapterInterface: AdapterInterface, val context:Context
) :
    RecyclerView.Adapter<MenuChildAdapter.ViewHolder>(){
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
        var date: TextView =view.findViewById(R.id.tvDate)
        var price: TextView =view.findViewById(R.id.tvFoodPrice)
        var name: TextView =view.findViewById(R.id.tvName)
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

        holder.contents.text=list[position].contents
        holder.name.text=list[position].name



        holder.fabCustomer.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + list[position].phone)
            context.startActivity(dialIntent)
        }




        holder.layout.setOnClickListener {
            val intent: Intent =Intent(context, OrderPanelActivity::class.java)
            intent.putExtra("phone",list[position].phone)
            intent.putExtra("id",list[position].OID)



            startActivity(context,intent,null)
        }
        holder.layout1.setOnClickListener {
            val intent: Intent =Intent(context, OrderPanelActivity::class.java)
            intent.putExtra("phone",list[position].phone)
            intent.putExtra("id",list[position].OID)



            startActivity(context,intent,null)
        }

    }




    override fun getItemCount(): Int {
        return list.size
    }

}
