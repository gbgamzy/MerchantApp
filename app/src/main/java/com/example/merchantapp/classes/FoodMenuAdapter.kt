package com.example.merchantapp.classes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.FoodMenu
import com.example.ajubamerchant.classes.Image
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.FoodListActivity
import com.example.merchantapp.OrderPanelActivity
import com.example.merchantapp.R


class FoodMenuAdapter(
        var list:ArrayList<FoodMenu>, var img:ArrayList<Image>, val adapterInterface: AdapterInterface, val context: Context
) :
        RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ticket_food_menu, parent, false)
        return ViewHolder(itemView)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        var name: TextView =view.findViewById(R.id.tvMenuTitle)

        var layout: LinearLayout =view.findViewById(R.id.llFoodMenu)
        var delete: Button = view.findViewById(R.id.btMenuDelete)



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=list[position].category
        holder.delete.setOnClickListener{

            adapterInterface.deleteMenu(list[position].category)
            notifyDataSetChanged()
        }
        holder.layout.setOnClickListener{
            val intent =Intent(context,FoodListActivity::class.java)
            intent.putExtra("category",list[position].category)
            startActivity(context,intent,null)
        }




    }



    override fun getItemCount(): Int {
        return list.size
    }

}
