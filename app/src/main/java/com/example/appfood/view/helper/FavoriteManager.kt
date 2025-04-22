package com.example.appfood.view.helper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appfood.model.domain.FoodModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavoriteManager {

    private val _favoriteItems = MutableLiveData<MutableList<FoodModel>>(mutableListOf())
    val favoriteItems: LiveData<MutableList<FoodModel>> get() = _favoriteItems

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private val userId: String? = FirebaseAuth.getInstance().currentUser?.uid
    private val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Favorites")

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        userId?.let { uid ->
            databaseRef.child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<FoodModel>()
                    for (child in snapshot.children) {
                        val item = child.getValue(FoodModel::class.java)
                        item?.let { list.add(it) }
                    }
                    _favoriteItems.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FavoriteManager", "Failed to load favorites: ${error.message}")
                }
            })
        }
    }

    fun toggleFavorite(item: FoodModel) {
        userId?.let { uid ->
            val currentList = _favoriteItems.value ?: mutableListOf()
            val isAlreadyFavorite = currentList.any { it.Id == item.Id }

            if (isAlreadyFavorite) {
                databaseRef.child(uid).child(item.Id.toString()).removeValue()
                _toastMessage.value = "${item.Title} removed from favorites"
            } else {
                val favoriteItem = item.copy(isFavorite = true)
                databaseRef.child(uid).child(item.Id.toString()).setValue(favoriteItem)
                _toastMessage.value = "${item.Title} added to favorites"
            }
        }
    }

    fun isFavorite(item: FoodModel): Boolean {
        return _favoriteItems.value?.any { it.Id == item.Id } == true
    }

    fun removeFavorite(food: FoodModel) {
        userId?.let { uid ->
            databaseRef.child(uid).child(food.Id.toString()).removeValue()
            _toastMessage.value = "${food.Title} removed from favorites"
        }
    }
}
