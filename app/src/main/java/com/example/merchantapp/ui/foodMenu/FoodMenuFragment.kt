package com.example.merchantapp.ui.foodMenu

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ajubamerchant.classes.*
import com.example.merchantapp.R
import com.example.merchantapp.adapters.FoodMenuAdapter
import com.example.merchantapp.classes.DNALOG
import com.example.merchantapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_add_menu.view.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@AndroidEntryPoint
class FoodMenuFragment : Fragment(),AdapterInterface {

    private lateinit var foodMenuViewModel: FoodMenuViewModel
    private var _binding: FragmentGalleryBinding? = null
    var lis:ArrayList<FoodMenu> = ArrayList()
    var img:ArrayList<Image> = ArrayList()


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
        foodMenuViewModel =
            ViewModelProvider(this).get(FoodMenuViewModel::class.java)
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val view = binding.root
        var ad=FoodMenuAdapter(lis,img,this,requireContext())
        binding.rvFoodMenu.layoutManager= LinearLayoutManager(context)

        binding.rvFoodMenu.adapter=ad


        binding.fabFoodMenu.setOnClickListener{
            val view=LayoutInflater.from(context).inflate(R.layout.dialog_add_menu,null)
            val dialog= context?.let { it1 -> AlertDialog.Builder(it1) }
            if (dialog != null) {
                dialog.setTitle("Add Food Category!")
                dialog.setView(view)
                dialog.setPositiveButton("Add",{ dialogInterface: DialogInterface, i: Int ->
                    addMenu(view.editTextTextPersonName.text.toString())

                })
                dialog.create().show()
            }





        }


        foodMenuViewModel.foodMenu.observe(viewLifecycleOwner,{
            lis.clear()
            lis.addAll(it)

            ad!!.notifyDataSetChanged()
        })
        foodMenuViewModel.images1.observe(viewLifecycleOwner,{
            img.clear()
            img.addAll(it)

            ad!!.notifyDataSetChanged()
        })







        return view
    }

    private fun addMenu(s: String) {

        CoroutineScope(Dispatchers.IO).launch{
            foodMenuViewModel.addMenu(s)
            refresh()
        }
    }
    private fun refresh(){
        CoroutineScope(Dispatchers.Main).launch{
            foodMenuViewModel.refresh()
        }
    }

    override fun getRiders1(): ArrayList<DeliveryBoy> {
        TODO("Not yet implemented")
    }

    override fun acceptOrder(_id: Int, o: Order) {
        TODO("Not yet implemented")
    }

    override fun deleteMenu(category: String) {
        CoroutineScope(Dispatchers.Main).launch{
            DNALOG.show("delte menu",category)
            foodMenuViewModel.deleteMenu(category)
            val l=lis.find{
                it.category==category
            }

            refresh()
        }


    }

    override fun deleteRider(phone: String?) {
        TODO("Not yet implemented")
    }

    override fun deleteFood(category: String, food: Food) {
        TODO("Not yet implemented")
    }


}