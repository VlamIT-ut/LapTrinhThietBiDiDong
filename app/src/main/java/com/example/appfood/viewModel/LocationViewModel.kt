@file:Suppress("DEPRECATION")

package com.example.appfood.viewModel

import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import java.util.*

class LocationViewModel : ViewModel() {
    var selectedPoint by mutableStateOf<GeoPoint?>(null) // 🆕 State observable
    var selectedAddress by mutableStateOf<String?>(null) // 🆕 State observable
    var suggestions by mutableStateOf<List<String>>(emptyList()) // 🆕 State observable

    fun updateSelectedPoint(point: GeoPoint) {
        selectedPoint = point
    }

    fun updateSelectedAddress(address: String) {
        selectedAddress = address
    }

    fun setMapCenter(point: GeoPoint) {
        selectedPoint = point
    }

    fun searchAddressSuggestions(context: Context, query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale("vi", "VN"))
                val results = geocoder.getFromLocationName(query, 5)
                if (results != null) {
                    suggestions = results.mapNotNull { it.getAddressLine(0) }
                }
            } catch (e: Exception) {
                suggestions = emptyList()
            }
        }
    }
}
