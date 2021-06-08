package com.example.merchantapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.Food
import com.example.ajubamerchant.classes.Image
import com.example.merchantapp.R


class FoodListAdapter(
    var list: List<Food>,
    var img: ArrayList<Image>,
    val adapterInterface: AdapterInterface,
    val context: Context,
    val category:String
) :
    RecyclerView.Adapter<FoodListAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ticket_food_list, parent, false)
        Log.d("adap",list.toString())
        return ViewHolder(itemView)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        var name: TextView =view.findViewById(R.id.textViewFoodListName)
        var price: TextView =view.findViewById(R.id.textViewFoodListPrice)


        var delete: ImageButton = view.findViewById(R.id.buttonFoodListDelete)
        var image: ImageView = view.findViewById(R.id.ivFoodList)
        var avail:Switch=view.findViewById(R.id.switch1)



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=list[position].name

        holder.avail.isChecked = list[position].available==1

        holder.avail.setOnCheckedChangeListener { _, state ->
            if(state){
                list[position].available=1
                adapterInterface.enableItem(list[position].FUID)

            }
            else{
                list[position].available=0
                adapterInterface.disableItem(list[position].FUID)
            }

        }


        holder.delete.setOnClickListener{
            val dialog= AlertDialog.Builder(context)
            dialog.setTitle("Confirm delete?")
            dialog.setPositiveButton("Yes"){ _: DialogInterface, _: Int ->
                adapterInterface.deleteFood(category,list[position])

            }
            dialog.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

            }
            dialog.create().show()



        }
        holder.price.text = list[position].price.toString()
        holder.image.setImageBitmap(img.find {
            it.name==list[position].name
        }?.image)




    }





    override fun getItemCount(): Int {
        return list.size
    }

}
