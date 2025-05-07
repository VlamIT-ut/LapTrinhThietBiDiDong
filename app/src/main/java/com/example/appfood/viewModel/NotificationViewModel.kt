package com.example.appfood.viewModel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfood.view.helper.NotificationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val _notifications = MutableStateFlow<List<String>>(emptyList())
    private val _unreadCount = mutableStateOf(0)
    private val notificationHelper = NotificationHelper(application.applicationContext)
    private val sharedPrefs = application.getSharedPreferences("notifications", Context.MODE_PRIVATE)

    val notifications = _notifications.asStateFlow()
    val unreadCount: State<Int> = _unreadCount

    init {
        loadNotifications()
    }

    fun addNotification(message: String) {
        viewModelScope.launch {
            val formattedMessage = "${getCurrentTime()} - $message"
            val newList = listOf(formattedMessage) + _notifications.value
            _notifications.value = newList
            _unreadCount.value += 1

            // Save to SharedPreferences
            sharedPrefs.edit().apply {
                putStringSet("notifications", newList.toSet())
                putInt("unread_count", _unreadCount.value)
                apply()
            }

            notificationHelper.showNotification("Food Delivery", message)
        }
    }

    private fun loadNotifications() {
        val savedNotifications = sharedPrefs.getStringSet("notifications", emptySet()) ?: emptySet()
        _notifications.value = savedNotifications.toList().sortedByDescending { it }
        _unreadCount.value = sharedPrefs.getInt("unread_count", 0)
    }

    fun markAllAsRead() {
        _unreadCount.value = 0
        sharedPrefs.edit().putInt("unread_count", 0).apply()
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}