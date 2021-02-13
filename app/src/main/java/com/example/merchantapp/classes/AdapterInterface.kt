package com.example.ajubamerchant.classes

interface AdapterInterface {
    abstract fun getRiders1(): ArrayList<DeliveryBoy>
    abstract fun acceptOrder(_id: String, o: Order)
    abstract fun deleteMenu(category:String)


}