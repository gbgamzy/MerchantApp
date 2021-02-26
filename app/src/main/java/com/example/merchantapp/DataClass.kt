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
        var _id:String,
        var contents:List<Content>,
        var price: Int,
        var date: String,
        var status:String,
        var address:Address,
        var customerPhone:String="",
        var deliveryBoy: DeliveryBoy = DeliveryBoy("","")

)
@Parcelize
data class ord(
    var _id:String,
    var contents:@RawValue List<Content>,
    var price: Int,
    var date: String,
    var status:String,
    var address:@RawValue Address,
    var customerPhone:String="",
    var deliveryBoy:@RawValue DeliveryBoy = DeliveryBoy("","")


):Parcelable
@Parcelize
data class Rider(
        var name:String?,
        var phone:String?,
        var orders: @RawValue List<ord>?

):Parcelable
data class Image(
        var name: String?,
        var image: Bitmap?
)

data class Food(

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
    var name:String="",
    var phone:String=""
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
