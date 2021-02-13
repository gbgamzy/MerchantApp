@file:Suppress("DEPRECATION")

package com.example.merchantapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ajubamerchant.classes.AdapterInterface
import com.example.ajubamerchant.classes.DeliveryBoy
import com.example.ajubamerchant.classes.Order
import com.example.ajubamerchant.classes.ViewPagerAdapter
import com.example.merchantapp.R
import com.example.merchantapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment(),AdapterInterface {

    private lateinit var homeViewModel: HomeViewModel
    var pending:ArrayList<Order> =ArrayList()
    var processing:ArrayList<Order> =ArrayList()
    var dispatched:ArrayList<Order> =ArrayList()
    var riders:ArrayList<DeliveryBoy> =ArrayList()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.swipeRefreshHome.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                refresh()
                binding.swipeRefreshHome.isRefreshing=false
            }


        })
                refresh()
        val adapter=ViewPagerAdapter(pending,dispatched,processing,requireContext(),this)
        binding.vpHome.adapter=adapter


        homeViewModel.pending?.observe(viewLifecycleOwner,Observer<ArrayList<Order>>{
            pending.clear()
            pending.addAll(it)

            adapter.notifyDataSetChanged()


        })

        homeViewModel.processing?.observe(viewLifecycleOwner,Observer<ArrayList<Order>>{
            processing.clear()
            processing.addAll(it)

            adapter.notifyDataSetChanged()



        })

        homeViewModel.dispatched?.observe(viewLifecycleOwner, {
            dispatched.clear()
            dispatched.addAll(it)

            adapter.notifyDataSetChanged()


        })

        return view
    }

    override fun getRiders1(): ArrayList<DeliveryBoy> {
        val l=homeViewModel.riders
        Log.d("vmHomeRiders",l.toString())

        return l

    }

    override fun acceptOrder(_id: String, o: Order) {
        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.dispatch(_id,o)
            Log.d("HomeAccept",_id+o.toString())
        }
    }

    override fun deleteMenu(category: String) {
        TODO("Not yet implemented")
    }

    fun refresh(){

        CoroutineScope(Dispatchers.Main).launch {
            try{ homeViewModel.refreshHome() }
            catch (err:Exception){Log.d("vmHomeRefresh",err.toString())}
        }

    }
}