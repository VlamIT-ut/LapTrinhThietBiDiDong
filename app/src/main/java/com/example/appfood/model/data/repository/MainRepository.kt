package com.example.appfood.model.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appfood.model.domain.BannerModel
import com.example.appfood.model.domain.CategoryModel
import com.example.appfood.model.domain.FoodModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadCategory():LiveData<MutableList<CategoryModel>>{
        Log.d("Firebase", "Database URL: ${firebaseDatabase.reference.database.reference}")
        val listData:MutableLiveData<MutableList<CategoryModel>> =MutableLiveData()
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Firebase", "Data snapshot: ${snapshot.value}")
                val list = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children){
                    val item=childSnapshot.getValue(CategoryModel::class.java)
                    Log.d("Firebase", "Category item: $item")
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load category: ${error.message}")
            }

        })
        return listData
    }



    fun loadBanner():LiveData<MutableList<BannerModel>>{
        Log.d("Firebase", "Database URL: ${firebaseDatabase.reference.database.reference}")
        val listData:MutableLiveData<MutableList<BannerModel>> =MutableLiveData()
        val ref = firebaseDatabase.getReference("Banners")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Firebase", "Data snapshot: ${snapshot.value}")
                val list = mutableListOf<BannerModel>()
                for (childSnapshot in snapshot.children){
                    val item=childSnapshot.getValue(BannerModel::class.java)
                    Log.d("Firebase", "Banner item: $item")
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load banners: ${error.message}")
            }

        })
        return listData
    }
    fun loadFiltered(id: String): LiveData<MutableList<FoodModel>> {
        val listData = MutableLiveData<MutableList<FoodModel>>()
        val ref = firebaseDatabase.getReference("Foods")
        val query: Query = ref.orderByChild("CategoryId").equalTo(id)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<FoodModel>()
                for (childSnapshot in snapshot.children) {
                    val food = childSnapshot.getValue(FoodModel::class.java)
                    food?.let {
                        lists.add(it)
                    }
                }
                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load foods: ${error.message}")
            }
        })

        return listData
    }
    fun loadFoodDetail(foodId: String): MutableLiveData<FoodModel?> {
        val foodData = MutableLiveData<FoodModel?>()
        val ref = firebaseDatabase.getReference("Foods").child(foodId)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val food = snapshot.getValue(FoodModel::class.java)
                foodData.value = food
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load food detail: ${error.message}")
            }
        })

        return foodData
    }

}