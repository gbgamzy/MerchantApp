@file:Suppress("DEPR ECATION")

package com.example.merchantapp

import android.Manifest.permission.*
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ajubamerchant.classes.Food
import com.example.ajubamerchant.classes.Network
import com.example.merchantapp.classes.DNASnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_food.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.create
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import javax.inject.Inject


@AndroidEntryPoint
class FoodActivity : AppCompatActivity() {
    @Inject
    lateinit var api: Network
    var picUri: Uri? = null
    var mBitmap: Bitmap? = null
    private var permissionsToRequest: ArrayList<String>? = null
    private val permissionsRejected: ArrayList<String> = ArrayList()
    private val permissions: ArrayList<String> = ArrayList()
    private val ALL_PERMISSIONS_RESULT = 107
    private val IMAGE_RESULT = 200
    private var selectedImageUri: Uri? = null


    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)


        btSelectImage.setOnClickListener {
            openImageChooser()
        }

        btFoodDone.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
            uploadImage()}
        }

    }
    private suspend fun multipartImageUpload() {
        try {
            if(etFoodName.text.isNotEmpty() && etFoodPrice.text.isNotEmpty()) {




                val filesDir = applicationContext.filesDir
                val file = File(filesDir, "image" + ".png")
                val bos = ByteArrayOutputStream()
                mBitmap!!.compress(Bitmap.CompressFormat.PNG, 0, bos)
                val bitmapdata: ByteArray = bos.toByteArray()
                val fos = FileOutputStream(file)
                fos.write(bitmapdata)
                fos.flush()
                fos.close()
                val reqFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body: MultipartBody.Part = MultipartBody.Part.createFormData("upload", file.name, reqFile)
                val id: RequestBody? = intent.extras?.getString("category")?.toRequestBody("text/plain".toMediaTypeOrNull())

                val p=api.postImage(body, id).body()
                val b= p?.let {
                    Food("",etFoodName.text.toString(),etFoodPrice.text.toString().toInt(),
                            it.message,0)
                }

                var _id= b?.let { api.addFood(intent.extras?.getString("category").toString(), it).body() }
                if(_id?.message=="SUCCESS"){
                    finish()
                }


            }
            else{
                DNASnackBar.show(this,
                        "Please Enter the Details!")
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private suspend fun uploadImage() {
        if (mBitmap != null)
            multipartImageUpload();
        else {
            Toast.makeText(getApplicationContext(), "Bitmap is null. Try again", Toast.LENGTH_SHORT).show();
        }
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    ivFoodImage.setImageURI(selectedImageUri)
                    mBitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri)
                }
            }
        }
    }


}