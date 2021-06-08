package com.example.merchantapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.R


class ViewPagerAdapter(

    val pending:ArrayList<Order>, val processing:ArrayList<Order>, val dispatched:ArrayList<Order>,
    private val context: Context, private val i: AdapterInterface,var todayOrders:ArrayList<Order>
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
        val a=i.getRiders1()
        if(position==0) {
            val adapter: MenuChildAdapter = MenuChildAdapter(pending, adapterInterface = i,context)
            holder.rv.layoutManager= LinearLayoutManager(context)
            holder.rv.itemAnimator= DefaultItemAnimator()
            holder.rv.adapter=adapter
            adapter.notifyDataSetChanged()
        }

        else if (position==1){

            var names:ArrayList<String> = ArrayList()
            a.forEach {
                it.deliveryBoyName?.let { it1 -> names.add(it1) }
            }
            Log.d("names",a.toString()+names.toString())
            val adapter: ProcessingAdapter = ProcessingAdapter(processing,a,names, adapterInterface = i,context)
            holder.rv.layoutManager= LinearLayoutManager(context)
            holder.rv.itemAnimator= DefaultItemAnimator()
            holder.rv.adapter=adapter
            adapter.notifyDataSetChanged()

        }
        else if(position==2){
            val adapter: DispatchedAdapter = DispatchedAdapter(dispatched,a,i,context)
            holder.rv.layoutManager= LinearLayoutManager(context)
            holder.rv.itemAnimator= DefaultItemAnimator()
            holder.rv.adapter=adapter
            adapter.notifyDataSetChanged()



        }
        else if(position==3){
            val adapter:TodayOrdersAdapter= TodayOrdersAdapter(todayOrders,a,context)
            holder.rv.layoutManager= LinearLayoutManager(context)
            holder.rv.itemAnimator= DefaultItemAnimator()
            holder.rv.adapter=adapter
            adapter.notifyDataSetChanged()

        }



    }



    override fun getItemCount(): Int {
        return 4
    }
}