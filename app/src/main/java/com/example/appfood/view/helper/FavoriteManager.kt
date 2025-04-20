package com.example.appfood.view.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appfood.model.domain.FoodModel

class FavoriteManager {

    private val _favoriteItems = MutableLiveData<MutableList<FoodModel>>(mutableListOf())
    val favoriteItems: LiveData<MutableList<FoodModel>> get() = _favoriteItems

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun toggleFavorite(item: FoodModel) {
        val currentList = _favoriteItems.value ?: mutableListOf()
        val isAlreadyFavorite = currentList.any { it.Id == item.Id }

        if (isAlreadyFavorite) {
            currentList.removeAll { it.Id == item.Id }
            _toastMessage.value = "${item.Title} removed from favorites"
        } else {
            currentList.add(item.copy(isFavorite = true))
            _toastMessage.value = "${item.Title} added to favorites"
        }

        _favoriteItems.value = currentList
    }

    fun isFavorite(item: FoodModel): Boolean {
        return _favoriteItems.value?.any { it.Id == item.Id } == true
    }

    fun removeFavorite(food: FoodModel) {
        val current = _favoriteItems.value?.toMutableList() ?: mutableListOf()
        current.removeAll { it.Id == food.Id }
        _favoriteItems.value = current
        _toastMessage.value = "${food.Title} removed from favorites"
    }
}
