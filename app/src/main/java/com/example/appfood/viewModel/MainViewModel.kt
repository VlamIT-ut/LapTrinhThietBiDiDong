package com.example.appfood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appfood.model.data.repository.MainRepository
import com.example.appfood.model.domain.BannerModel
import com.example.appfood.model.domain.CategoryModel
import com.example.appfood.model.domain.FoodModel



class MainViewModel :ViewModel(){
    private val repository = MainRepository()
    fun loadBanner():LiveData<MutableList<BannerModel>>{
        return repository.loadBanner()
    }
    fun loadCategory():LiveData<MutableList<CategoryModel>>{
        return repository.loadCategory()
    }
    fun loadFiltered(id: String): LiveData<MutableList<FoodModel>> {
        return repository.loadFiltered(id)
    }
    fun loadFoodDetail(foodId: String): MutableLiveData<FoodModel?> {
        return repository.loadFoodDetail(foodId)
    }



}