

package com.example.merchantapp.ui.home

import android.content.Context
import android.content.SharedPreferences
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
import com.example.ajubamerchant.classes.Food
import com.example.ajubamerchant.classes.Order
import com.example.merchantapp.adapters.ViewPagerAdapter
import com.example.merchantapp.classes.DNASnackBar
import com.example.merchantapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment(),AdapterInterface {


    private lateinit var homeViewModel: HomeViewModel
    var pending:ArrayList<Order> =ArrayList()
    var dispatched:ArrayList<Order> =ArrayList()
    var processing:ArrayList<Order> =ArrayList()
    var todayOrders:ArrayList<Order> =ArrayList()
    val formatter= SimpleDateFormat("dd MM yyyy HH.mm")
    var date: Date?=Date()

    lateinit var pref: SharedPreferences
    lateinit var edit: SharedPreferences.Editor


    var riders:ArrayList<DeliveryBoy> =ArrayList()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()

        refresh()

    }

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
        pref= activity?.getSharedPreferences("appSharedPrefs", Context.MODE_PRIVATE)!!

        edit=pref.edit()

        val adapter= ViewPagerAdapter(pending,processing,dispatched,requireContext(),this,todayOrders)
        binding.vpHome.adapter=adapter
        binding.tbHome.isInlineLabel=true

        TabLayoutMediator(binding.tbHome, binding.vpHome){ tab, position->
            if(position==0){
                tab.text="Pending"
            }
            else if(position==1){
                tab.text="Processing"
            }
            else if(position==2){
                tab.text="Dispatched"

            }
            else if(position==3){
                tab.text="Completed"
            }

        }.attach()




        homeViewModel.pending?.observe(viewLifecycleOwner,Observer<ArrayList<Order>>{
            pending.clear()
            pending.addAll(it)

            adapter.notifyDataSetChanged()


        })
        homeViewModel.todayOrder?.observe(viewLifecycleOwner,Observer<ArrayList<Order>>{
            todayOrders.clear()
            todayOrders.addAll(it)

            adapter.notifyDataSetChanged()


        })
        homeViewModel.dispatched.observe(viewLifecycleOwner,{
            dispatched.clear()
            dispatched.addAll(it)
            adapter.notifyDataSetChanged()


        })

        homeViewModel.processing?.observe(viewLifecycleOwner,Observer<ArrayList<Order>>{
            processing.clear()
            processing.addAll(it)

            adapter.notifyDataSetChanged()



        })



        return view
    }

    override fun getRiders1(): ArrayList<DeliveryBoy> {
        val l=homeViewModel.riders
        Log.d("vmHomeRiders",l.toString())

        return l

    }

    override fun acceptOrder(_id: Int, o: Order) {
        CoroutineScope(Dispatchers.Main).launch {
            homeViewModel.dispatch(_id,o)
            Log.d("HomeAccept",_id.toString())
        }
        refresh()
    }

    override fun deleteMenu(category: String) {
        TODO("Not yet implemented")
    }

    override fun deleteRider(phone: String?) {
        TODO("Not yet implemented")
    }

    override fun deleteFood(category: String, food: Food) {
        TODO("Not yet implemented")
    }

    override fun setRider(oid: Int, o: Order) {
        CoroutineScope(Dispatchers.Main).launch {
            homeViewModel.setRider(oid, o)
        }
    }

    override fun enableItem(fuid: Int?) {
        TODO("Not yet implemented")
    }

    override fun disableItem(fuid: Int?) {
        TODO("Not yet implemented")
    }

    fun refresh(){

        try{
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    var token=""
                    FirebaseMessaging.getInstance().token.addOnCompleteListener {
                        if (it.isSuccessful) {
                            token= it.result.toString()


                        }
                    }

                    pref.getString("phone","786")?.let { homeViewModel.refreshHome(it,token) }
                    var d=""
                    var m=""
                    if(date?.date.toString().length==1)
                        d="0"+date?.date.toString()
                    else
                        d=date?.date.toString()
                    if(((date?.month)?.plus(1)).toString().length==1)
                        m="0"+((date?.month)?.plus(1)).toString()
                    else
                        m=((date?.month)?.plus(1)).toString()


                    homeViewModel.getTodayOrders(d,m,(1900+ date?.year!!).toString())
                } catch (err: Exception) {
                    Log.d("vmHomeRefresh", err.toString())
                }
            }
        }
        catch(err:Exception){
            DNASnackBar.show(context,"We are facing some problems!")
        }

    }
}