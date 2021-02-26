@file:Suppress("DEPRECATION")

package com.example.merchantapp.adapters

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.R
import kotlinx.android.synthetic.main.dialog_spinner.view.*

class ProcessingAdapter(
        var list:ArrayList<Order>,var riders:List<DeliveryBoy>,var names:ArrayList<String>, val adapterInterface: AdapterInterface, val context: Context
) :

        RecyclerView.Adapter<ProcessingAdapter.ViewHolder>() , AdapterView.OnItemSelectedListener{
    var name:String=""



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ticket_view_pager, parent, false)

        return ViewHolder(itemView)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var image: ImageView =view.findViewById(R.id.imageView5)
        var name: TextView =view.findViewById(R.id.textView2)

        var price: TextView =view.findViewById(R.id.tvFoodPrice)
        var layout: LinearLayout =view.findViewById(R.id.llOrder)



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.order_status_processing))


        holder.price.text= list[position].price.toString()
        var s=""
        list[position].contents.forEach {
            s+= it.name+" x "+it.quantity+" "
        }
        holder.name.text=s
        holder.layout.setOnClickListener {
            val view=LayoutInflater.from(context).inflate(R.layout.dialog_spinner,null)
            val dialog=AlertDialog.Builder(context)
            dialog.setTitle("Select Rider !")
            dialog.setView(view)

            val spinner=view.findViewById<Spinner>(R.id.spinner)
            var l = mutableListOf<String>()

                l.addAll(names)

            val ad =ArrayAdapter(context,android.R.layout.simple_spinner_item,l)

            ad.setDropDownViewResource(
                    android.R.layout
                            .simple_spinner_dropdown_item)
            spinner.adapter=ad
            with(spinner){
                onItemSelectedListener=this@ProcessingAdapter
            }

            dialog.setPositiveButton("Done"){ dialogInterface: DialogInterface, i: Int ->
                done(position)
            }

            dialog.create().show()






        }

    }
    fun done(position:Int){
        if(name!=""){
            val r= riders?.find {
                it.name==name
            }
            val o=list[position]
            if (r != null) {
                o.deliveryBoy=r
            }

            adapterInterface.acceptOrder(o._id,o)



        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        name= names?.get(position).toString()
        Log.d("vmselected",name)


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}
