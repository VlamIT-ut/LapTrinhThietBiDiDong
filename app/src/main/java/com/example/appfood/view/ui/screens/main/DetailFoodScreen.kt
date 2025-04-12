package com.example.appfood.view.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.appfood.model.domain.FoodModel
import com.example.appfood.view.detailEachFood.DescriptionSection
import com.example.appfood.view.detailEachFood.FooterSection
import com.example.appfood.view.detailEachFood.HeaderSection
import com.example.appfood.view.helper.ManagmentCart

@Composable
fun DetailFoodScreen(
    item: FoodModel,
    onBackClick: () -> Unit,
    onAddToCartClick: (FoodModel) -> Unit
) {
    var numberInCart by remember { mutableStateOf(item.numberInCart) }
    val context = LocalContext.current
    val managementCart = remember { ManagmentCart(context) } // Initialize your cart management with context

    ConstraintLayout {
        val (footer, column) = createRefs()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .constrainAs(column) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .padding(bottom = 80.dp),
        ) {
            HeaderSection(
                item = item,
                numberInCart = numberInCart,
                onIncrement = {
                    numberInCart++
                },
                onDecrement = {
                    if (numberInCart > 1) {
                        numberInCart--
                    }
                },
                onBackClick = onBackClick
            )
            DescriptionSection(item.Description)
        }
        FooterSection(
            onAddToCartClick = {
                // Create a copy of the item to avoid modifying the original
                val updatedItem = item.copy(numberInCart = numberInCart)
                managementCart.insertItem(updatedItem)
                onAddToCartClick(updatedItem) // Notify the caller that the item was added to the cart
            },
            totalPrice = (item.Price * numberInCart),
            modifier = Modifier.constrainAs(footer) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
                .navigationBarsPadding()
        )
    }
}