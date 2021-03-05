package com.example.ajubamerchant.classes


import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface   Network {
    @POST("/Ajuba/admin/{phone}/{registrationToken}")
    suspend fun login(@Path("phone")phone:String , @Path("registrationToken")registrationToken:String)
    :Response<Message>

    @GET("/Ajuba/admin/getDetails/{phone}")
    suspend fun getDetails(@Path("phone") phone: String): Response<List<Order>>

    @POST("/Ajuba/acceptOrder/{id}")
    suspend fun acceptOrder(@Path("id") id: Int, @Body order: Order): Response<Message>

    @POST("/Ajuba/processOrder/{id}")
    suspend fun processOrders(@Path("id") id: String): Response<Message>

    @POST("/Ajuba/rejectOrder/{id}")
    suspend fun rejectOrders(@Path("id") id: String, @Body order: Order): Response<Message>

    @GET("/Ajuba/admin/getPendingOrders")
    suspend fun getPendingOrders(): Response<List<Order>>



    @GET("/Ajuba/admin/getProcessingOrders")
    suspend fun getProcessingOrders(): Response<List<Order>>

    @GET("/Ajuba/admin/getOrder/{id}")
    suspend fun getOrder(@Path("id") id: String):Response<Order>

    @GET("/Ajuba/admin/getRiders")
    suspend fun getRiders():Response<List<DeliveryBoy>>

    @Multipart
    @POST("/Ajuba/images")
    suspend fun postImage(@Part image: MultipartBody.Part, @Part("id") id:RequestBody?  ):Response<Message>

    @GET("/Ajuba/customer/menu")
    suspend fun getMenu():Response<List<FoodMenu>>

    @GET("/Ajuba/customer/food")
    suspend fun getFood():Response<List<Food>>

    @GET("/Ajuba/images/{img_id}")
    suspend fun getImage(@Path("img_id") id:String ):Response<ResponseBody?>

    @DELETE("/Ajuba/images/{img_id}")
    suspend fun deleteImage(@Path("img_id") id:String ):Response<Message>


    @DELETE("/Ajuba/admin/foodMenu/category/{category}")
    suspend fun deleteMenu(@Path("category")category:String):Response<Message>

    @POST("/Ajuba/admin/foodMenu/category/{category}")
    suspend fun addMenu(@Path("category")category:String)


    @POST("/Ajuba/admin/foodMenu/{category}/food")
    suspend fun addFood(@Path("category")category:String,@Body food:Food):Response<Message>



    @DELETE("/Ajuba/admin/foodMenu/{category}/food/{name}")
    suspend fun deleteFood(@Path("category")category:String,@Path("name")name:String):Response<Message>


    @GET("/Ajuba/admin/prices")
    suspend fun getPrices(): Response<Price>

    @POST("/Ajuba/admin/prices")
    suspend fun uploadPrices( @Body price: Price): Response<Message>

    @GET("/Ajuba/admin/getRidersList")
    suspend fun getRidersList():Response<List<DeliveryBoy>>

    @GET("/Ajuba/admin/rider/{id}/getOrders")
    suspend fun getRiderOrders(@Path("id")id:Int):Response<List<Order>>

    @POST("/Ajuba/admin/getRidersList")
    suspend fun uploadRider(@Body rider:DeliveryBoy):Response<Message>
    @DELETE("/Ajuba/admin/getRidersList/{phone}")
    suspend fun deleteRider(@Path("phone")phone:String):Response<Message>







}