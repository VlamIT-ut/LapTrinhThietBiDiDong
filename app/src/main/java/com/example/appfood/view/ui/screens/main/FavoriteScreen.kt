package com.example.appfood.view.ui.screens.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.appfood.R
import com.example.appfood.model.domain.FoodModel
import com.example.appfood.view.navigation.MyBottomBar
import com.example.appfood.viewModel.MainViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import java.text.DecimalFormat

@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val favoriteItems by viewModel.favoriteItems.observeAsState(emptyList())
    val context = LocalContext.current
    val toastMessage by viewModel.toastMessage.observeAsState()

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(R.drawable.back_grey),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable { navController.popBackStack() }
                        )
                        Text(
                            text = "Favorite",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.orange)
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                },
                backgroundColor = Color.White
            )
        },
        bottomBar = { MyBottomBar(navController) },
        content = { innerPadding ->
            if (favoriteItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No favorite items yet!")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    items(favoriteItems) { food ->
                        FavoriteItem(
                            item = food,
                            navController = navController,
                            onDeleteClick = {
                                viewModel.removeFavorite(food) // <- Xử lý xoá ở đây
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun FavoriteItem(
    item: FoodModel,
    navController: NavController,
    onDeleteClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .border(1.dp, colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
            .clickable {
                // 👉 Khi click vào toàn bộ item, điều hướng tới màn hình chi tiết
                navController.navigate("detail/${item.Id}")
            }
    ) {
        val (pic, titleTxt, feeEachTime, deleteBtn) = createRefs()
        val decimalFormat = DecimalFormat("#,##0.00")

        Image(
            painter = rememberAsyncImagePainter(item.ImagePath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(135.dp)
                .height(100.dp)
                .background(colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(pic) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = item.Title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .constrainAs(titleTxt) {
                    start.linkTo(pic.end)
                    top.linkTo(pic.top)
                }
                .padding(start = 8.dp, top = 8.dp)
        )

        Text(
            text = "$${decimalFormat.format(item.Price)}",
            fontSize = 16.sp,
            color = colorResource(R.color.darkPurple),
            modifier = Modifier
                .constrainAs(feeEachTime) {
                    start.linkTo(titleTxt.start)
                    top.linkTo(titleTxt.bottom)
                }
                .padding(start = 8.dp, top = 4.dp)
        )

        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .constrainAs(deleteBtn) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .clickable {
                    onDeleteClick()
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.i_delete),
                contentDescription = "Delete",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
