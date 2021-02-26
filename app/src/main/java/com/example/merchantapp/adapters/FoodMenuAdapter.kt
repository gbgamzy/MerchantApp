package com.example.merchantapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.FoodMenu
import com.example.ajubamerchant.classes.Image
import com.example.merchantapp.FoodListActivity
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

        var name: TextView =view.findViewById(R.id.textViewFoodListName)

        var layout: LinearLayout =view.findViewById(R.id.llFoodMenu)
        var delete: ImageButton = view.findViewById(R.id.buttonFoodListDelete)



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=list[position].category
        holder.delete.setOnClickListener{
            val dialog= AlertDialog.Builder(context)
            dialog.setTitle("Confirm delete?")
            dialog.setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                adapterInterface.deleteMenu(list[position].category)
                list.removeAt(position)
                notifyDataSetChanged()
            }
            dialog.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

            }
            dialog.create().show()


        }
        Log.d("adapter",list.toString())
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
