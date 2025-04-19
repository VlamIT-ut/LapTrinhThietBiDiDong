package com.example.appfood.model.data.repository

import com.example.appfood.model.domain.OrderModel
import com.google.firebase.database.FirebaseDatabase

class OrderRepository {
    private val database = FirebaseDatabase.getInstance().getReference("Orders")

    fun saveOrder(order: OrderModel, onResult: (Boolean) -> Unit) {
        val newOrderRef = database.push()
        order.orderId = newOrderRef.key ?: ""
        newOrderRef.setValue(order)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}
