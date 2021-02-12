package com.example.ajubamerchant.classes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.merchantapp.R
import com.example.merchantapp.classes.ProcessingAdapter
import com.example.merchantapp.ui.home.HomeFragment


class ViewPagerAdapter(

        val pending:ArrayList<Order>, val dispatched:ArrayList<Order>, val processing:ArrayList<Order>,
        private val context: Context, private val i: AdapterInterface
) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>(){


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var rv: RecyclerView =view.findViewById(R.id.rvViewPager)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_view_pager, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(position==0) {
            val adapter: MenuChildAdapter = MenuChildAdapter(pending, adapterInterface = i,context)
            holder.rv.layoutManager= LinearLayoutManager(context)
            holder.rv.itemAnimator= DefaultItemAnimator()
            holder.rv.adapter=adapter
            adapter.notifyDataSetChanged()
        }
        else if(position==2){
            val adapter: MenuChildAdapter = MenuChildAdapter(dispatched, adapterInterface = i,context)
            holder.rv.layoutManager= LinearLayoutManager(context)
            holder.rv.itemAnimator= DefaultItemAnimator()
            holder.rv.adapter=adapter
            adapter.notifyDataSetChanged()
        }
        else{
            val adapter: ProcessingAdapter = ProcessingAdapter(processing, adapterInterface = i,context)
            holder.rv.layoutManager= LinearLayoutManager(context)
            holder.rv.itemAnimator= DefaultItemAnimator()
            holder.rv.adapter=adapter
            adapter.notifyDataSetChanged()

        }
        Log.d("adapter",processing.toString())



    }



    override fun getItemCount(): Int {
        return 3
    }
}