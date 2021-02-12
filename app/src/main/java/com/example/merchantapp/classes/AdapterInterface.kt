package com.example.ajubamerchant.classes

interface AdapterInterface {
    abstract fun getRiders(): List<DeliveryBoy>?
    abstract fun acceptOrder(_id: String, o: Order)
}