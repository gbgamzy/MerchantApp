package com.example.merchantapp

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ajubamerchant.classes.*
import com.example.merchantapp.adapters.FoodListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_food_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

@AndroidEntryPoint
class FoodListActivity : AppCompatActivity(), AdapterInterface {
    var f:ArrayList<FoodMenu> = ArrayList()
    var d:ArrayList<Food> = ArrayList()
    var i:ArrayList<Image> = ArrayList()
    var category=""
    var adapter:FoodListAdapter = FoodListAdapter(d,i,this,this,category )

    @Inject
    lateinit var api: Network
    override fun onResume(){
        super.onResume()
        reload()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        val intent=intent.extras
        category= intent!!.getString("category").toString()
        adapter=FoodListAdapter(d,i,this,this,category)
        rvFoodItems.layoutManager= LinearLayoutManager(this)
        rvFoodItems.adapter=adapter

        floatingActionButtonAddFoodItem.setOnClickListener {
            val intent= Intent(this,FoodActivity::class.java)
            intent.putExtra("category",category)
            startActivity(intent)
        }
        try{
            reload()
        }
        catch(err: Exception){
            Log.d("imageError",err.toString())
        }





    }
    fun reload(){
        CoroutineScope(Dispatchers.Main).launch {
            val list = api.getMenu()?.body()
            f.clear()
            if (list != null) {
                f.addAll(list)
            }
            val p= f.find {
                it.category==category
            }
            d.clear()

            if(p?.list?.size  != 0){
                if (p != null) {
                    d.addAll(p.list)
                }
            }
            Log.d("FoodListDList",d.toString())
            try{
                reloadImages(d)
            }
            catch(err:Exception){
                Log.d("Iteratr error ",err.toString())
            }


            adapter.notifyDataSetChanged()



        }
    }
    suspend fun reloadImages(foodList: ArrayList<Food>) {
        var images=ArrayList<Image>()
        foodList.forEach{



            var body=api.getImage(it.image)?.body()
            if(body!=null){
            val futureStudioIconFile: File = File(this.getExternalFilesDir(null), File.separator + it.name + ".jpg")

            var inputStream: InputStream?=null
            var outputStream: OutputStream?=null

            val fileReader = ByteArray(4096)
            val fileSize = body!!.contentLength()
            var fileSizeDownloaded: Long = 0
            inputStream=body.byteStream()
            outputStream= FileOutputStream(futureStudioIconFile)

            while (true) {
                var read = inputStream.read(fileReader)


                if (read == -1) {
                    break
                }
                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded +=read

            }
            outputStream.flush()
            inputStream.close()
            outputStream.close()
            }






        }
        getImages(foodList)



    }
    suspend fun getImages(foodList: List<Food>){
        var images=ArrayList<Image>()
        foodList.forEach {

            var image= BitmapFactory.decodeFile(this.getExternalFilesDir(null).toString() + File.separator + it.name + ".jpg")
            var img=Image(name = it.name,image = image)
            images.add(img)
        }
        i.clear()
        i.addAll(images)
    }

    override fun getRiders1(): ArrayList<DeliveryBoy> {
        TODO("Not yet implemented")
    }

    override fun acceptOrder(_id: String, o: Order) {
        TODO("Not yet implemented")
    }

    override fun deleteMenu(category: String) {
        TODO("Not yet implemented")
    }

    override fun deleteRider(phone: String?) {
        TODO("Not yet implemented")
    }

    override fun deleteFood(category:String,food: Food) {
        CoroutineScope(Dispatchers.Main).launch {
        api.deleteFood(category,food.name)
            api.deleteImage(food.image)


        }
        reload()
    }
}