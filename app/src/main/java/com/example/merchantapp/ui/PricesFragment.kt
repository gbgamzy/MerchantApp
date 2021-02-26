package com.example.merchantapp.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ajubamerchant.classes.Network
import com.example.ajubamerchant.classes.Price
import com.example.merchantapp.R
import com.example.merchantapp.classes.DNASnackBar
import com.example.merchantapp.databinding.FragmentGalleryBinding
import com.example.merchantapp.databinding.FragmentPricesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_prices.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PricesFragment : Fragment(){
    @Inject
    lateinit var api: Network
    private val binding get() = _binding!!
    private var _binding: FragmentPricesBinding? = null

    private var price: Price?= null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPricesBinding.inflate(inflater, container, false)

        val root = binding.root
        CoroutineScope(Dispatchers.Main).launch{
            price=api.getPrices().body()
            if(price != null){
                binding.editTextNumber.setText(price!!.dist1.toString(), TextView.BufferType.EDITABLE)
                binding.editTextNumber2.setText(price!!.price1.toString(), TextView.BufferType.EDITABLE)
                binding.editTextNumber3.setText(price!!.dist2.toString(), TextView.BufferType.EDITABLE)
                binding.editTextNumber4.setText(price!!.price2.toString(), TextView.BufferType.EDITABLE)
                binding.editTextNumber5.setText(price!!.dist3.toString(), TextView.BufferType.EDITABLE)
                binding.editTextNumber6.setText(price!!.price3.toString(), TextView.BufferType.EDITABLE)

            }
        }

        binding.buttonPricesUpload.setOnClickListener{


            try{
                CoroutineScope(Dispatchers.IO).launch {
                    price?.dist1 = binding.editTextNumber.text.toString().toFloat()
                    price?.price1 = binding.editTextNumber2.text.toString().toFloat()
                    price?.dist2 = binding.editTextNumber3.text.toString().toFloat()
                    price?.price2 = binding.editTextNumber4.text.toString().toFloat()
                    price?.dist3 = binding.editTextNumber5.text.toString().toFloat()
                    price?.price3 = binding.editTextNumber6.text.toString().toFloat()
                    price?.let { it1 -> api.uploadPrices(price = it1) }

                }
                DNASnackBar.show(activity, "Uploaded")
            }
            catch(err:Exception){

            }
        }


        return root
    }
}