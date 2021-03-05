package com.example.merchantapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.DeliveryBoy

import com.example.merchantapp.CheckOrdersActivity
import com.example.merchantapp.R
import com.example.merchantapp.ui.riders.RiderFragment


class RiderAdapter(
        var list: ArrayList<DeliveryBoy>, val adapterInterface: AdapterInterface, val context: Context
) :
    RecyclerView.Adapter<RiderAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ticket_food_list, parent, false)
        return ViewHolder(itemView)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var name: TextView =view.findViewById(R.id.textViewFoodListName)
        var price: TextView =view.findViewById(R.id.textViewFoodListPrice)


        var delete: ImageButton = view.findViewById(R.id.buttonFoodListDelete)
        var image: ImageView = view.findViewById(R.id.ivFoodList)
        var touch: LinearLayout = view.findViewById(R.id.llTouch)




    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = list[position]
        holder.name.text = p.deliveryBoyName
        holder.price.text =p.deliveryBoyPhone

        holder.touch.setOnClickListener {
            val intent= Intent(context,CheckOrdersActivity::class.java)

            intent.putExtra("phone",list[position].DbID)
            startActivity(context,intent,null)
        }
        holder.delete.setOnClickListener{
            val dialog= AlertDialog.Builder(context)
            dialog.setTitle("Confirm delete?")
            dialog.setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                adapterInterface.deleteRider(p.deliveryBoyPhone)

            }
            dialog.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

            }
            dialog.create().show()

        }



    }



    override fun getItemCount(): Int {
        return list.size
    }

}
