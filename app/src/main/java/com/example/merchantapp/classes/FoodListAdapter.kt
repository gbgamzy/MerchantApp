package com.example.merchantapp.classes

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.Food
import com.example.ajubamerchant.classes.Image
import com.example.merchantapp.FoodActivity
import com.example.merchantapp.R
import kotlinx.android.synthetic.main.dialog_add_menu.view.*

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.create

import retrofit2.Retrofit
import retrofit2.http.Part


import java.io.File


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
            .inflate(R.layout.ticket_food_menu, parent, false)
        return ViewHolder(itemView)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        var name: TextView =view.findViewById(R.id.tvMenuTitle)

        var layout: LinearLayout =view.findViewById(R.id.llFoodMenu)
        var delete: Button = view.findViewById(R.id.btMenuDelete)



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=list[position].name
        holder.layout.setOnClickListener{
            val intent=Intent(context, FoodActivity::class.java)
            intent.putExtra("category",category)
            startActivity(context,intent,null)

        }
        holder.delete.setOnClickListener{

        }




    }





    override fun getItemCount(): Int {
        return list.size
    }

}
