package com.example.merchantapp.classes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ajubamerchant.classes.Order

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrder(element:Order?)

    

    @Query("SELECT * FROM orders WHERE status='A' ")
    suspend fun getPending():List<Order>

    @Query("SELECT * FROM orders WHERE status='A2' ")
    suspend fun getProcessing():List<Order>



    @Query("DELETE FROM orders WHERE OID=:id")
    suspend fun removeOrder(id:String):Unit 
    
    @Query("DELETE FROM orders ")
    suspend fun clearOrder():Unit

    @Query("SELECT * FROM orders WHERE OID=:id")
    suspend fun getOrder(id: kotlin.Int):Order





}