package com.example.merchantapp.ui.gallery

import android.app.Application
import android.graphics.BitmapFactory
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ajubamerchant.classes.Food
import com.example.ajubamerchant.classes.FoodMenu
import com.example.ajubamerchant.classes.Image
import com.example.ajubamerchant.classes.Network
import com.example.merchantapp.classes.HomeDao
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class GalleryViewModel @ViewModelInject constructor(val api: Network, val db: HomeDao,application: Application) : AndroidViewModel(application) {
    val images1:MutableLiveData<ArrayList<Image>> = MutableLiveData()
    val foodMenu:MutableLiveData<ArrayList<FoodMenu>> = MutableLiveData()
    private val context = getApplication<Application>().applicationContext


    suspend fun refresh(){
        val foodList:ArrayList<Food> = ArrayList()
        val images:ArrayList<Image> = ArrayList()
        val l=api.getMenu().body()
        var list:ArrayList<FoodMenu> =ArrayList()
        l?.let { list.addAll(it) }
        foodMenu.value=list
        list.forEach {
            foodList.addAll(it.list)

        }
        reloadImages(foodList)

    }
    suspend fun reloadImages(foodList: ArrayList<Food>) {
        var images=ArrayList<Image>()
        foodList.forEach{



            var body=api.getImage(it.image).body()
            val futureStudioIconFile: File = File(context.getExternalFilesDir(null), File.separator + it.name + ".jpg")

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
        getImages(foodList)



    }
    suspend fun getImages(foodList: ArrayList<Food>){
        var images=ArrayList<Image>()
        foodList.forEach {

            var image= BitmapFactory.decodeFile(context.getExternalFilesDir(null).toString() + File.separator + it.name + ".jpg")
            var img=Image(name = it.name,image = image)
            images.add(img)
        }
        images1.postValue(images)
    }

    suspend fun deleteMenu(category:String){
        val v=foodMenu.value?.find {
            it.category==category
        }
        if (v != null) {
            v.list.forEach {
                api.deleteImage(it.image)
            }
        }

        api.deleteMenu(category)
    }

    suspend fun addMenu(category:String){
        api.addMenu(category)
    }

}