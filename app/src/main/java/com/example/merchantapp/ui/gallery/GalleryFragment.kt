package com.example.merchantapp.ui.gallery

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ajubamerchant.classes.*
import com.example.merchantapp.R
import com.example.merchantapp.databinding.FragmentGalleryBinding
import com.example.merchantapp.databinding.FragmentHomeBinding
import com.example.merchantapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_add_menu.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@AndroidEntryPoint
class GalleryFragment : Fragment(),AdapterInterface {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null
    var lis:ArrayList<FoodMenu> = ArrayList<FoodMenu>()
    var img:ArrayList<Image> = ArrayList<Image>()

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val view = binding.root

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


        galleryViewModel.foodMenu.observe(viewLifecycleOwner,{
            lis=it
        })
        galleryViewModel.images1.observe(viewLifecycleOwner,{
            img=it
        })







        return view
    }

    private fun addMenu(s: String) {

        CoroutineScope(Dispatchers.IO).launch{
            galleryViewModel.addMenu(s)
        }
    }

    override fun getRiders1(): ArrayList<DeliveryBoy> {
        TODO("Not yet implemented")
    }

    override fun acceptOrder(_id: String, o: Order) {
        TODO("Not yet implemented")
    }

    override fun deleteMenu(category: String) {

    }
}