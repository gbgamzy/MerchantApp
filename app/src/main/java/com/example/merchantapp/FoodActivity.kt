package com.example.merchantapp

import android.Manifest.permission.*
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ajubamerchant.classes.Network
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_food.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.create
import java.io.*


@AndroidEntryPoint
class FoodActivity : AppCompatActivity() {
    lateinit var api: Network
    var picUri: Uri? = null
    var mBitmap: Bitmap? = null
    private var permissionsToRequest: ArrayList<String>? = null
    private val permissionsRejected: ArrayList<String> = ArrayList()
    private val permissions: ArrayList<String> = ArrayList()
    private val ALL_PERMISSIONS_RESULT = 107
    private val IMAGE_RESULT = 200
    private var selectedImageUri: Uri? = null
    val REQUEST_CODE_PICK_IMAGE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        askPermissions()
        btSelectImage.setOnClickListener {
            openImageChooser()
        }

        btFoodDone.setOnClickListener {
            //uploadImage()
        }

    }
    private suspend fun multipartImageUpload() {
        try {
            val filesDir = applicationContext.filesDir
            val file = File(filesDir, "image" + ".png")
            val bos = ByteArrayOutputStream()
            mBitmap!!.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata: ByteArray = bos.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            val reqFile: RequestBody = create("image/*".toMediaTypeOrNull(), file)
            val body: MultipartBody.Part = MultipartBody.Part.createFormData("upload", file.name, reqFile)
            val id: RequestBody = create("text/plain".toMediaTypeOrNull(), "id")
            api.postImage(body, id)

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
        startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
    }
    private fun getCaptureImageOutputUri(): Uri? {
        var outputFileUri: Uri? = null
        val getImage: File? = getExternalFilesDir("")
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.getPath(), "profile.png"))
        }
        return outputFileUri
    }

    fun getPickImageChooserIntent(): Intent? {
        val outputFileUri: Uri = getCaptureImageOutputUri()!!
        val allIntents: MutableList<Intent> = ArrayList()
        val packageManager = packageManager
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            }
            allIntents.add(intent)
        }
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            val intent = Intent(galleryIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            allIntents.add(intent)
        }
        var mainIntent = allIntents[allIntents.size - 1]
        for (intent in allIntents) {
            if (intent.component!!.className == "com.android.documentsui.DocumentsActivity") {
                mainIntent = intent
                break
            }
        }
        allIntents.remove(mainIntent)
        val chooserIntent = Intent.createChooser(mainIntent, "Select source")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray())
        return chooserIntent
    }


    private fun askPermissions() {
        permissions.add(CAMERA)
        permissions.add(WRITE_EXTERNAL_STORAGE)
        permissions.add(READ_EXTERNAL_STORAGE)
        permissionsToRequest = findUnAskedPermissions(permissions)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest?.size!! > 0) permissionsToRequest?.toArray(
                arrayOfNulls<String>(permissionsToRequest!!.size)
            )?.let {
                requestPermissions(
                    it, ALL_PERMISSIONS_RESULT
                )
            }
        }
    }
    private fun findUnAskedPermissions(wanted: ArrayList<String>): ArrayList<String>? {
        val result = ArrayList<String>()
        for (perm in wanted) {
            if (!hasPermission(perm)) {
                result.add(perm)
            }
        }
        return result
    }
    private fun hasPermission(permission: String): Boolean {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }
    private fun canMakeSmores(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }


}