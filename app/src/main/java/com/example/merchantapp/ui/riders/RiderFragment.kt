package com.example.merchantapp.ui.riders

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ajubamerchant.classes.*
import com.example.merchantapp.R
import com.example.merchantapp.adapters.RiderAdapter
import com.example.merchantapp.databinding.FragmentSlideshowBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_add_rider.*
import kotlinx.android.synthetic.main.dialog_add_rider.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RiderFragment : Fragment(),AdapterInterface {

    private lateinit var riderViewModel: RiderViewModel
    var list:ArrayList<DeliveryBoy> = ArrayList()
    var adapter:RiderAdapter ?= null
    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        reload()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        riderViewModel =
                ViewModelProvider(this).get(RiderViewModel::class.java)
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root = binding.root
        adapter= RiderAdapter(list,this,requireContext())
        binding.rvRiderHome.layoutManager= LinearLayoutManager(requireContext())
        binding.rvRiderHome.adapter=adapter
        binding.floatingActionButtonAddRiderHome.setOnClickListener{
            val view=LayoutInflater.from(context).inflate(R.layout.dialog_add_rider,null)
            val dialog= AlertDialog.Builder(context)
            dialog.setView(view)
            dialog.setTitle("Add Rider")
            dialog.setPositiveButton("Done"){ dialogInterface: DialogInterface, i: Int ->
                var r:DeliveryBoy = DeliveryBoy(0,view.etRiderName.text.toString() ,view.etRiderPhone.text.toString())
                Log.d("vmvmRider",r.toString())
               CoroutineScope(Dispatchers.IO).launch{

                   riderViewModel.addRider(r)
               }

            }

            dialog.create().show()
        }
        binding.swipeRefreshLayoutRiderHome.setOnRefreshListener {
            reload()
            binding.swipeRefreshLayoutRiderHome.isRefreshing=false
        }

        


        riderViewModel.riders.observe(viewLifecycleOwner){
            list.clear()
            list.addAll(it)
            Log.d("List",list.toString())
            adapter!!.notifyDataSetChanged()

        }


        return root
    }
    fun reload(){
        CoroutineScope(Dispatchers.IO).launch{ riderViewModel.reload() }
    }

    override fun getRiders1(): ArrayList<DeliveryBoy> {
        TODO("Not yet implemented")
    }

    override fun acceptOrder(_id: Int, o: Order) {
        TODO("Not yet implemented")
    }

    override fun deleteMenu(category: String) {
        TODO("Not yet implemented")
    }

    override fun deleteRider(phone: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            if (phone != null) {
                riderViewModel.deleteRider(phone)
            }
        }
    }

    override fun deleteFood(category: String, food: Food) {
        TODO("Not yet implemented")
    }
}
