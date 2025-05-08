package com.example.appfood.viewModel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfood.view.helper.NotificationHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val _notifications = MutableStateFlow<List<String>>(emptyList())
    private val _unreadCount = mutableStateOf(0)
    private val notificationHelper = NotificationHelper(application.applicationContext)
    private var lastNotificationCount = 0

    val notifications = _notifications.asStateFlow()
    val unreadCount: State<Int> = _unreadCount

    private val userId: String? = FirebaseAuth.getInstance().currentUser?.uid
    private val notifRef: DatabaseReference? = userId?.let {
        FirebaseDatabase.getInstance().getReference("notifications").child(it)
    }

    init {
        loadNotifications()
    }

    fun addNotification(message: String) {
        viewModelScope.launch {
            notifRef?.let { ref ->
                val notifId = ref.push().key ?: System.currentTimeMillis().toString()
                ref.child(notifId).setValue(message)
                notificationHelper.showNotification("Food Delivery", message)
            }
        }
    }

    private fun loadNotifications() {
        notifRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<String>()
                for (child in snapshot.children) {
                    child.getValue(String::class.java)?.let { list.add(it) }
                }
                if (list.size > lastNotificationCount) {
                    _unreadCount.value += (list.size - lastNotificationCount)
                }
                lastNotificationCount = list.size
                _notifications.value = list.reversed()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun clearAllNotifications() {
        notifRef?.removeValue()
        _notifications.value = emptyList()
        _unreadCount.value = 0
    }

    fun markAllAsRead() {
        _unreadCount.value = 0
        lastNotificationCount = _notifications.value.size
    }
}