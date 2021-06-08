package com.example.ajubamerchant.classes

interface AdapterInterface {
    abstract fun getRiders1(): ArrayList<DeliveryBoy>
    abstract fun acceptOrder(_id: Int, o: Order)
    abstract fun deleteMenu(category:String)
    abstract fun deleteRider(phone: String?)
    abstract fun deleteFood(category:String,food: Food)
    abstract fun setRider(oid: Int, o: Order)
    abstract fun enableItem(fuid: Int?)
    abstract fun disableItem(fuid: Int?)


}