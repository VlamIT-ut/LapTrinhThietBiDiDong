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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

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

    fun saveAddressToFirebase(onResult: ((Boolean) -> Unit)? = null) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onResult?.invoke(false)
            return
        }
        if (selectedAddress.isNullOrBlank() || selectedPoint == null) {
            onResult?.invoke(false)
            return
        }
        val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId)
        userRef.child("address").setValue(selectedAddress)
        userRef.child("lat").setValue(selectedPoint!!.latitude)
        userRef.child("lng").setValue(selectedPoint!!.longitude)
            .addOnCompleteListener { task ->
                onResult?.invoke(task.isSuccessful)
            }
    }

    fun loadAddressFromFirebase() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val address = snapshot.child("address").getValue(String::class.java)
                val lat = snapshot.child("lat").getValue(Double::class.java)
                val lng = snapshot.child("lng").getValue(Double::class.java)
                if (address != null && lat != null && lng != null) {
                    selectedAddress = address
                    selectedPoint = GeoPoint(lat, lng)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
