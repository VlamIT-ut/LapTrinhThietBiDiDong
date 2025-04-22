package com.example.appfood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.appfood.model.data.local.UserPreferences
import com.example.appfood.model.data.repository.MainRepository
import com.example.appfood.model.domain.BannerModel
import com.example.appfood.model.domain.CategoryModel
import com.example.appfood.model.domain.FoodModel
import com.example.appfood.view.helper.FavoriteManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    val appLanguage = userPreferences.appLanguage.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "en"
    )

    fun toggleLanguage() {
        viewModelScope.launch {
            val newLang = if (appLanguage.value == "en") "vi" else "en"
            userPreferences.setAppLanguage(newLang)
        }
    }
    private val repository = MainRepository()
    val favoriteManager = FavoriteManager() // <- public để sử dụng ngoài ViewModel

    val favoriteItems: LiveData<MutableList<FoodModel>> = favoriteManager.favoriteItems
    val toastMessage: LiveData<String> = favoriteManager.toastMessage

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        return repository.loadBanner()
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadFiltered(id: String): LiveData<MutableList<FoodModel>> {
        return repository.loadFiltered(id)
    }

    fun loadFoodDetail(foodId: String): MutableLiveData<FoodModel?> {
        return repository.loadFoodDetail(foodId)
    }
    private val _foodList = MutableStateFlow<List<FoodModel>>(emptyList())
    val foodList: StateFlow<List<FoodModel>> = _foodList

    init {
        println("MainViewModel: Khởi tạo.")
        loadAllFoods()
    }

    fun loadAllFoods() {
        viewModelScope.launch {
            println("MainViewModel: Gọi loadAllFoods().")
            repository.loadAllFoods().collect { foods ->
                println("MainViewModel: loadAllFoods() nhận được dữ liệu: $foods")
                _foodList.value = foods
            }
            println("MainViewModel: loadAllFoods() hoàn thành.")
        }
    }
    companion object {
        fun provideFactory(
            userPreferences: UserPreferences
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(userPreferences) as T
            }
        }
    }
    fun toggleFavorite(item: FoodModel) = favoriteManager.toggleFavorite(item)
    fun isFavorite(item: FoodModel): Boolean = favoriteManager.isFavorite(item)
    fun removeFavorite(item: FoodModel) = favoriteManager.removeFavorite(item)
}
