package com.example.appfood.view.ui.screens.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appfood.model.domain.FoodModel
import com.example.appfood.view.detailEachFood.DescriptionSection
import com.example.appfood.view.detailEachFood.FooterSection
import com.example.appfood.view.detailEachFood.HeaderSection
import com.example.appfood.view.helper.ManagementCart
import com.example.appfood.viewModel.MainViewModel

@Composable
fun DetailFoodScreen(
    item: FoodModel,
    onBackClick: () -> Unit,
    onAddToCartClick: (FoodModel) -> Unit,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var numberInCart by remember { mutableIntStateOf(item.numberInCart) }
    val managementCart = remember { ManagementCart(context) }

    // Quan sát danh sách yêu thích và cập nhật lại trạng thái
    val favoriteList by viewModel.favoriteManager.favoriteItems.observeAsState(emptyList())
    val isFavorite = favoriteList.any { it.Id == item.Id }

    // Quan sát và hiển thị Toast nếu có thông báo
    val toastMessage by viewModel.favoriteManager.toastMessage.observeAsState()
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

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
                onIncrement = { numberInCart++ },
                onDecrement = { if (numberInCart > 1) numberInCart-- },
                onBackClick = onBackClick,
                isFavorite = isFavorite,
                onFavoriteClick = {
                    viewModel.favoriteManager.toggleFavorite(item)
                }
            )
            DescriptionSection(item.Description)
        }

        FooterSection(
            onAddToCartClick = {
                val updatedItem = item.copy(numberInCart = numberInCart)
                managementCart.insertItem(updatedItem)
                onAddToCartClick(updatedItem)
            },
            totalPrice = item.Price * numberInCart,
            modifier = Modifier
                .constrainAs(footer) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .navigationBarsPadding()
        )
    }
}
