package com.example.appfood.view.helper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appfood.model.domain.FoodModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavoriteManager {
    private val TAG = "FavoriteManager"

    private val _favoriteItems = MutableLiveData<MutableList<FoodModel>>(mutableListOf())
    val favoriteItems: LiveData<MutableList<FoodModel>> get() = _favoriteItems

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private val userId: String? = FirebaseAuth.getInstance().currentUser?.uid
    private val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Favorites")

    init {
        Log.d(TAG, "Initializing FavoriteManager")
        Log.d(TAG, "Current user ID: $userId")
        loadFavorites()
    }

    private fun loadFavorites() {
        userId?.let { uid ->
            Log.d(TAG, "Loading favorites for user: $uid")
            databaseRef.child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<FoodModel>()
                    for (child in snapshot.children) {
                        val item = child.getValue(FoodModel::class.java)
                        item?.let { list.add(it) }
                    }
                    Log.d(TAG, "Loaded ${list.size} favorite items")
                    _favoriteItems.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Failed to load favorites: ${error.message}")
                }
            })
        } ?: run {
            Log.e(TAG, "Cannot load favorites: User not logged in")
        }
    }

    fun toggleFavorite(item: FoodModel) {
        userId?.let { uid ->
            Log.d(TAG, "Toggling favorite for item: ${item.Title} (ID: ${item.Id})")
            val currentList = _favoriteItems.value ?: mutableListOf()
            val isAlreadyFavorite = currentList.any { it.Id == item.Id }
            Log.d(TAG, "Is already favorite: $isAlreadyFavorite")

            if (isAlreadyFavorite) {
                databaseRef.child(uid).child(item.Id.toString()).removeValue()
                    .addOnSuccessListener {
                        Log.d(TAG, "Successfully removed from favorites")
                        _toastMessage.value = "${item.Title} removed from favorites"
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Failed to remove from favorites: ${e.message}")
                    }
            } else {
                val favoriteItem = item.copy(isFavorite = true)
                databaseRef.child(uid).child(item.Id.toString()).setValue(favoriteItem)
                    .addOnSuccessListener {
                        Log.d(TAG, "Successfully added to favorites")
                        _toastMessage.value = "${item.Title} added to favorites"
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Failed to add to favorites: ${e.message}")
                    }
            }
        } ?: run {
            Log.e(TAG, "Cannot toggle favorite: User not logged in")
            _toastMessage.value = "Please login to add favorites"
        }
    }

    fun isFavorite(item: FoodModel): Boolean {
        return _favoriteItems.value?.any { it.Id == item.Id } == true
    }

    fun removeFavorite(food: FoodModel) {
        userId?.let { uid ->
            Log.d(TAG, "Removing favorite: ${food.Title} (ID: ${food.Id})")
            databaseRef.child(uid).child(food.Id.toString()).removeValue()
                .addOnSuccessListener {
                    Log.d(TAG, "Successfully removed from favorites")
                    _toastMessage.value = "${food.Title} removed from favorites"
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Failed to remove from favorites: ${e.message}")
                }
        } ?: run {
            Log.e(TAG, "Cannot remove favorite: User not logged in")
        }
    }
}
