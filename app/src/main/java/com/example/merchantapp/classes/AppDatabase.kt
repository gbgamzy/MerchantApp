package com.example.merchantapp.classes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ajubamerchant.classes.Order
import com.example.ajubamerchant.classes.TypeConverter


@Database(entities = [Order::class], version = 3,exportSchema = false)
@TypeConverters(TypeConverter::class)


abstract class AppDatabase : RoomDatabase() {

    abstract fun homeDao():HomeDao




}