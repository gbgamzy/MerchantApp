package com.example.ajubamerchant.classes


import com.example.ajubamerchant.classes.Message
import com.example.ajubamerchant.classes.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Network {

    @GET("/Ajuba/admin/getDetails/{phone}")
    suspend fun getDetails(@Path("phone")phone:String): Response<List<Order>>

    @POST("/Ajuba/admin/acceptOrder/{id}")
    suspend fun acceptOrder(@Path("id")id:String,order: Order): Response<Message>

    @POST("/Ajuba/admin/processOrder/{id}")
    suspend fun processOrders(@Path("id")id:String): Response<Message>

    @POST("/Ajuba/admin/rejectOrder/{id}")
    suspend fun rejectOrders(@Path("id")id:String,@Body order: Order): Response<Message>

    @GET("/Ajuba/admin/getPendingOrders")
    suspend fun getPendingOrders(): Response<List<Order>>


    @GET("/Ajuba/admin/getDispatchedOrders")
    suspend fun getDispatchedOrders(): Response<List<Order>>

    @GET("/Ajuba/admin/getProcessingOrders")
    suspend fun getProcessingOrders(): Response<List<Order>>

    @GET("/Ajuba/admin/getOrder/{id}")
    suspend fun getOrder(@Path("id")id:String):Response<Order>

    @GET("/Ajuba/admin/getRiders")
    suspend fun getRiders():Response<List<DeliveryBoy>>


}