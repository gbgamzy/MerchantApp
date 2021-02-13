package com.example.ajubamerchant.classes


import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface Network {

    @GET("/Ajuba/admin/getDetails/{phone}")
    suspend fun getDetails(@Path("phone") phone: String): Response<List<Order>>

    @POST("/Ajuba/admin/acceptOrder/{id}")
    suspend fun acceptOrder(@Path("id") id: String, @Body order: Order): Response<Message>

    @POST("/Ajuba/admin/processOrder/{id}")
    suspend fun processOrders(@Path("id") id: String): Response<Message>

    @POST("/Ajuba/admin/rejectOrder/{id}")
    suspend fun rejectOrders(@Path("id") id: String, @Body order: Order): Response<Message>

    @GET("/Ajuba/admin/getPendingOrders")
    suspend fun getPendingOrders(): Response<List<Order>>


    @GET("/Ajuba/admin/getDispatchedOrders")
    suspend fun getDispatchedOrders(): Response<List<Order>>

    @GET("/Ajuba/admin/getProcessingOrders")
    suspend fun getProcessingOrders(): Response<List<Order>>

    @GET("/Ajuba/admin/getOrder/{id}")
    suspend fun getOrder(@Path("id") id: String):Response<Order>

    @GET("/Ajuba/admin/getRiders")
    suspend fun getRiders():Response<List<DeliveryBoy>>

    @Multipart
    @POST("/Ajuba/images")
    suspend fun postImage(@Part image: MultipartBody.Part, @Part("id") id:RequestBody?  )

    @GET("/Ajuba/customer/menu")
    suspend fun getMenu():Response<List<FoodMenu>>

    @GET("/Ajuba/images/{img_id}")
    suspend fun getImage(@Path("img_id") id:String ):Response<ResponseBody>

    @DELETE("/Ajuba/images/{img_id}")
    suspend fun deleteImage(@Path("img_id") id:String )


    @DELETE("/Ajuba/admin/foodMenu/{category}")
    suspend fun deleteMenu(@Path("category")category:String)

    @POST("/Ajuba/admin/foodMenu/{category}")
    suspend fun addMenu(@Path("category")category:String)


    @POST("/Ajuba/admin/foodMenu/{category}/food")
    suspend fun addFood(@Path("category")category:String,@Body food:Food):Response<Message>



    @DELETE("/Ajuba/admin/foodMenu/{category}/food/{name}")
    suspend fun deleteFood(@Path("category")category:String,@Path("name")name:String)






}