package com.example.merchantapp.ui.foodMenu

import android.app.Application
import android.graphics.BitmapFactory
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ajubamerchant.classes.Food
import com.example.ajubamerchant.classes.FoodMenu
import com.example.ajubamerchant.classes.Image
import com.example.ajubamerchant.classes.Network
import com.example.merchantapp.classes.DNALOG
import com.example.merchantapp.classes.HomeDao
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class FoodMenuViewModel @ViewModelInject constructor(val api: Network, val db: HomeDao, application: Application) : AndroidViewModel(application) {
    val images1:MutableLiveData<ArrayList<Image>> = MutableLiveData()
    val foodMenu:MutableLiveData<ArrayList<FoodMenu>> = MutableLiveData()
    private val context = getApplication<Application>().applicationContext


    suspend fun refresh(){
        val foodList:ArrayList<Food> = ArrayList()
        val images:ArrayList<Image> = ArrayList()
        val l=api.getMenu().body()
        Log.d("menu",l.toString())
        var list:ArrayList<FoodMenu> =ArrayList()
        l?.let { list.addAll(it) }
        foodMenu.value=list
        list.forEach {
            foodList.addAll(it.list)

        }


    }

    suspend fun deleteMenu(category:String){

        api.deleteMenu(category)

    }
    suspend fun deleteFood(category: String, food: Food){
        api.deleteFood(category,food.name)
    }
    suspend fun deleteImage(id: String){
        api.deleteImage(id)
    }

    suspend fun addMenu(category:String){
        api.addMenu(category)
    }

}