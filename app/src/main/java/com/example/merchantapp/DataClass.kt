package com.example.ajubamerchant.classes

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*
@Entity(tableName = "orders")

data class Order(
        @PrimaryKey(autoGenerate = false)
        var OID:Int?,
        var contents:String?,
        var price: Int?,
        var date: String?,
        var status:String?,
        var AID:Int?,
        var houseName:String?,
        var streetAddress:String?,
        var latitude:Double?,
        var longitude:Double?,
        var deliveryBoyName:String?,
        var deliveryBoyPhone:String?,


        var phone:String="",



)


data class Image(
        var name: String?,
        var image: Bitmap?
)

data class Food(
        var category:String?,
        var name:String,
        var price:Int,
        var image:String,
        var quantity:Int=0

)

data class FoodMenu(

        var category:String,

        var list:List<Food>

){


}

class TypeConverter{
    @TypeConverter
    fun fromContents(value:List<Content>?)= Gson().toJson(value)
    @TypeConverter
    fun toContents(value:String?)= Gson().fromJson(value,Array<Content>::class.java).toList()
    @TypeConverter
    fun fromDeliveryBoy(value:DeliveryBoy?)= Gson().toJson(value)
    @TypeConverter
    fun toDeliveryBoy(value:String?)= Gson().fromJson(value,DeliveryBoy::class.java)
    @TypeConverter
    fun fromAddress(value:Address?)= Gson().toJson(value)
    @TypeConverter
    fun toAddress(value:String?)= Gson().fromJson(value,Address::class.java)
    @TypeConverter
    fun fromFoodList(value:List<Food>?)= Gson().toJson(value)
    @TypeConverter
    fun toFoodList(value:String?)= Gson().fromJson(value,Array<Food>::class.java).toList()
    @TypeConverter
    fun fromContent(value:List<String>?)=Gson().toJson(value)
    @TypeConverter
    fun toContent(value:String?)= Gson().fromJson(value,Array<String>::class.java).toList()

}
data class DeliveryBoy(
        var DbID:Int?,
        var deliveryBoyName:String?,
        var deliveryBoyPhone:String?,
)
data class Message(
    var message:String
)
data class Content(
    var name:String="",
    var quantity:Int=0
)
data class Address(
    var houseName:String,
    var streetAddress:String,
    var latitude:Double,
    var longitude:Double,
)
data class Price(
    var minimumDistance:Float?,
    var minimumPrice:Float?,
    var dist1:Float?,
    var price1:Float?,
    var dist2:Float?,
    var price2:Float?,
    var dist3:Float?,
    var price3:Float?
)
data class Admin(
    var phone:String?,
    var registrationToken:String?
)
