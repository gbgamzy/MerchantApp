package com.example.merchantapp

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ajubamerchant.classes.Food
import com.example.ajubamerchant.classes.FoodMenu
import com.example.ajubamerchant.classes.Image
import com.example.ajubamerchant.classes.Network
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class FoodListActivity : AppCompatActivity() {
    var f:ArrayList<FoodMenu> = ArrayList()
    var i:ArrayList<Image> = ArrayList()

    lateinit var api: Network
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        val intent=intent.extras
        val category=intent?.getString("category")
        CoroutineScope(Dispatchers.IO).launch {
            val list = api.getMenu().body()
            f.clear()
            if (list != null) {
                f.addAll(list)
            }
            val p= f.find {
                it.category==category
            }
            if (p != null) {
                getImages(p.list)
            }


        }

    }
    suspend fun getImages(foodList: List<Food>){
        var images=ArrayList<Image>()
        foodList.forEach {

            var image= BitmapFactory.decodeFile(this.getExternalFilesDir(null).toString() + File.separator + it.name + ".jpg")
            var img=Image(name = it.name,image = image)
            images.add(img)
        }
        i.clear()
        i=images
    }
}