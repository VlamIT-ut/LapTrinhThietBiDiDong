package com.example.appfood.viewModel

import androidx.lifecycle.ViewModel
import com.example.appfood.model.domain.OrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrderViewModel : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderModel>>(emptyList())
    val orders: StateFlow<List<OrderModel>> = _orders

    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Orders")

    fun loadOrders() {
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return

        dbRef.orderByChild("userId").equalTo(currentUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<OrderModel>()
                    for (child in snapshot.children) {
                        val order = child.getValue(OrderModel::class.java)
                        order?.let {
                            // Fallback nếu thiếu trường isCancelled
                            if (child.child("isCancelled").value == null) {
                                it.isCancelled = false
                            }
                            list.add(it)
                        }
                    }
                    _orders.value = list
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }


    fun deleteOrder(orderId: String) {
        dbRef.child(orderId).removeValue().addOnSuccessListener {
            loadOrders() // refresh danh sách
        }
    }

}
