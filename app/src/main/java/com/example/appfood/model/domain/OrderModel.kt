package com.example.appfood.model.domain

import java.io.Serializable

data class OrderModel(
    var orderId: String = "",
    var userId: String = "",
    var items: List<FoodModel> = emptyList(),
    var total: Double = 0.0,
    var tax: Double = 0.0,
    var deliveryFee: Double = 0.0,
    var paymentMethod: String = "",
    var address: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var timestamp: Long = System.currentTimeMillis()
) : Serializable
