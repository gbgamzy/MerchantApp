package com.example.merchantapp.ui.foodMenu

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ajubamerchant.classes.Food
import com.example.ajubamerchant.classes.FoodMenu
import com.example.ajubamerchant.classes.Image
import com.example.merchantapp.classes.Network
import com.example.merchantapp.classes.HomeDao

class FoodMenuViewModel @ViewModelInject constructor(val api: Network, val db: HomeDao, application: Application) : AndroidViewModel(application) {
    val images1:MutableLiveData<ArrayList<Image>> = MutableLiveData()
    val foodMenu:MutableLiveData<ArrayList<FoodMenu>> = MutableLiveData()
    private val context = getApplication<Application>().applicationContext


    suspend fun refresh(){
        val foodList:ArrayList<Food> = ArrayList()
        val images:ArrayList<Image> = ArrayList()
        try{ val l = api.getMenu().body()
            Log.d("menu",l.toString())
            var list:ArrayList<FoodMenu> =ArrayList()
            l?.let { list.addAll(it) }
            foodMenu.value=list}
        catch(err:Exception){
            Log.d("Connecterror",err.toString())
        }




    }

    suspend fun deleteMenu(category:String){

        try{ api.deleteMenu(category) }
        catch(err:Exception){

        }

    }
    suspend fun deleteFood(category: String, food: Food){
        try{ api.deleteFood(category, food.name)  }
        catch(err:Exception){

        }
    }
    suspend fun deleteImage(id: String){
        try{ api.deleteImage(id) }
        catch(err:Exception){

        }
    }

    suspend fun addMenu(category:String){
        try{ api.addMenu(category) }
        catch(err:Exception){

        }
    }

}