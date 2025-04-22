package com.example.appfood.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationViewModel : ViewModel() {
    private val _notifications = mutableStateListOf<String>()
    private val _unreadCount = mutableStateOf(0)

    val notifications: List<String> = _notifications
    val unreadCount: State<Int> = _unreadCount

    fun addNotification(message: String) {
        _notifications.add(0, "${getCurrentTime()} - $message")
        _unreadCount.value++
    }

    fun markAllAsRead() {
        _unreadCount.value = 0
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
