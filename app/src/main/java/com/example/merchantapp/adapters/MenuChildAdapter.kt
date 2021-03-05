package com.example.merchantapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.OrderPanelActivity
import com.example.merchantapp.R


class MenuChildAdapter(
    var list:ArrayList<Order>, val adapterInterface: AdapterInterface, val context:Context
) :
    RecyclerView.Adapter<MenuChildAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ticket_view_pager, parent, false)
        return ViewHolder(itemView)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var image: ImageView =view.findViewById(R.id.imageView5)
        var name: TextView =view.findViewById(R.id.textView2)

        var price: TextView =view.findViewById(R.id.tvFoodPrice)
        var layout:LinearLayout=view.findViewById(R.id.llOrder)



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.price.text= list[position].price.toString()


        holder.name.text=list[position].contents
        holder.layout.setOnClickListener {
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
