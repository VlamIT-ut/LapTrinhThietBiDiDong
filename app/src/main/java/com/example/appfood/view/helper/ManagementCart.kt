package com.example.appfood.view.helper

import android.content.Context
import android.widget.Toast
import com.example.appfood.model.domain.FoodModel

class ManagementCart(private val context: Context) {

    private val tinyDB = TinyDB(context)

    fun insertItem(item: FoodModel) {
        val listFood = getListCart()
        val index = listFood.indexOfFirst { it.Title == item.Title }

        if (index != -1) {
            listFood[index].numberInCart = item.numberInCart
        } else {
            listFood.add(item)
        }

        tinyDB.putListObject("CartList", listFood)
        Toast.makeText(context, "${item.Title} added to your cart", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<FoodModel> {
        return tinyDB.getListObject("CartList") ?: arrayListOf()
    }

    fun minusItem(listFood: ArrayList<FoodModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (position in listFood.indices) {
            val item = listFood[position]
            if (item.numberInCart <= 1) {
                listFood.removeAt(position)
                Toast.makeText(context, "${item.Title} removed from cart", Toast.LENGTH_SHORT).show()
            } else {
                item.numberInCart -= 1
            }
            tinyDB.putListObject("CartList", listFood)
            listener.onChanged()
        }
    }

    fun plusItem(listFood: ArrayList<FoodModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (position in listFood.indices) {
            listFood[position].numberInCart++
            tinyDB.putListObject("CartList", listFood)
            listener.onChanged()
        }
    }

    fun getTotalFee(): Double {
        return getListCart().sumOf { it.Price * it.numberInCart }
    }

    fun removeItem(position: Int, listener: ChangeNumberItemsListener) {
        val listFood = getListCart()
        if (position in listFood.indices) {
            val removedItem = listFood.removeAt(position)
            tinyDB.putListObject("CartList", listFood)
            listener.onChanged()
            Toast.makeText(context, "${removedItem.Title} removed from cart", Toast.LENGTH_SHORT).show()
        }
    }
}
