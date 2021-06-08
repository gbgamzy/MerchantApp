@file:Suppress("DEPRECATION")

package com.example.merchantapp.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.R
import com.example.merchantapp.classes.DNASnackBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.dialog_spinner.view.*
import java.text.SimpleDateFormat


class ProcessingAdapter(
        var list:ArrayList<Order>,var riders:List<DeliveryBoy>,var names:ArrayList<String>, val adapterInterface: AdapterInterface, val context: Context
) :

        RecyclerView.Adapter<ProcessingAdapter.ViewHolder>() , AdapterView.OnItemSelectedListener{
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
        var rider: TextView =view.findViewById(R.id.tvRider)
        var address: TextView =view.findViewById(R.id.tvAddress)
        var date: TextView =view.findViewById(R.id.tvDate)
        var layout: LinearLayout =view.findViewById(R.id.llOrder)
        var layout1: LinearLayout =view.findViewById(R.id.llOrder1)
        var fabCustomer: FloatingActionButton = view.findViewById(R.id.fabCustomer)
        var fabRider: FloatingActionButton = view.findViewById(R.id.fabRider)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text= "₹ "+list[position].price.toString()
        val address=list[position].houseName+", "+list[position].streetAddress
        holder.address.text=address

        val ri= riders.find {
            it.deliveryBoyPhone==list[position].deliveryBoy
        }



        holder.flag.setBackgroundResource(R.drawable.order_status_processing)
        holder.contents.text=list[position].contents
        holder.name.text=list[position].name
        holder.rider.text=ri?.deliveryBoyName


        holder.layout.setOnClickListener {
            if(list[position].deliveryBoy==null||list[position].deliveryBoy==""){
                DNASnackBar.show(context,"Select rider, from call rider button")
                return@setOnClickListener
            }

            val dialog=AlertDialog.Builder(context)
            dialog.setTitle("Dispatch order?")
            dialog.setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                adapterInterface.acceptOrder(list[position].OID!!,list[position])

            }
            dialog.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

            }

            dialog.create().show()
        }
        holder.layout1.setOnClickListener {
            if(list[position].deliveryBoy==null||list[position].deliveryBoy==""){
                DNASnackBar.show(context,"Select rider, from call rider button")
                return@setOnClickListener
            }

            val dialog=AlertDialog.Builder(context)
            dialog.setTitle("Dispatch order?")
            dialog.setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                adapterInterface.acceptOrder(list[position].OID!!,list[position])

            }
            dialog.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

            }

            dialog.create().show()
        }


        holder.fabCustomer.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + list[position].phone)
            context.startActivity(dialIntent)


        }
        val i=formatter.parse(list[position].date)
        var date=format.format(i)
        holder.date.text=date

        holder.fabRider.setOnLongClickListener(OnLongClickListener {
            val view=LayoutInflater.from(context).inflate(R.layout.dialog_spinner,null)
            val dialog= AlertDialog.Builder(context)
            dialog.setTitle("Select Rider !")
            dialog.setView(view)

            val spinner=view.findViewById<Spinner>(R.id.spinner)
            var l = mutableListOf<String>()

            l.addAll(names)

            val ad = ArrayAdapter(context,android.R.layout.simple_spinner_item,l)

            ad.setDropDownViewResource(
                android.R.layout
                    .simple_spinner_dropdown_item)
            spinner.adapter=ad
            with(spinner){
                onItemSelectedListener=this@ProcessingAdapter
            }

            dialog.setPositiveButton("Done"){ dialogInterface: DialogInterface, i: Int ->
                if(name==""){
                    DNASnackBar.show(context,"Select a rider")
                }
                else
                    setRider(position)
            }

            dialog.create().show()


            true // <- set to true
        })
        holder.fabRider.setOnClickListener {
            if(list[position].deliveryBoy==null){
                val view=LayoutInflater.from(context).inflate(R.layout.dialog_spinner,null)
                val dialog= AlertDialog.Builder(context)
                dialog.setTitle("Select Rider !")
                dialog.setView(view)

                val spinner=view.findViewById<Spinner>(R.id.spinner)
                var l = mutableListOf<String>()

                l.addAll(names)

                val ad = ArrayAdapter(context,android.R.layout.simple_spinner_item,l)

                ad.setDropDownViewResource(
                    android.R.layout
                        .simple_spinner_dropdown_item)
                spinner.adapter=ad
                with(spinner){
                    onItemSelectedListener=this@ProcessingAdapter
                }

                dialog.setPositiveButton("Done"){ dialogInterface: DialogInterface, i: Int ->
                    if(name==""){
                        DNASnackBar.show(context,"Select a rider")
                    }
                    else
                    setRider(position)
                }

                dialog.create().show()




            }
            else{
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + list[position].deliveryBoy)
                context.startActivity(dialIntent)
            }
        }

    }

    fun setRider(position:Int){
        if(name!=""){
            val r= riders?.find {
                it.deliveryBoyName==name
            }
            val o=list[position]
            if (r != null) {

                o.deliveryBoy=r.deliveryBoyPhone
                Log.d("Updated Rider",o.toString())
            }

            adapterInterface.setRider(o.OID!!,o)
            notifyItemChanged(position)



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
        name=""
    }

}
