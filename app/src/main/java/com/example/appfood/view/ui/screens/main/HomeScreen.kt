package com.example.appfood.view.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.model.domain.BannerModel
import com.example.appfood.model.domain.CategoryModel
import com.example.appfood.view.dashboard.*
import com.example.appfood.view.navigation.MyBottomBar
import com.example.appfood.view.navigation.TopBar
import com.example.appfood.viewModel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfood.model.data.local.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModel.provideFactory(UserPreferences(LocalContext.current))
    )
    val banners = remember { mutableStateListOf<BannerModel>() }
    val categories = remember { mutableStateListOf<CategoryModel>() }
    var showBannerLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }
    val foodList by viewModel.foodList.collectAsState()

    var searchText by remember { mutableStateOf("") }
    val filteredFoodList by remember(foodList, searchText) {
        derivedStateOf {
            foodList.filter { it.Title.contains(searchText, ignoreCase = true) }
        }
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Load dữ liệu
    LaunchedEffect(Unit) {
        viewModel.loadBanner().observeForever {
            banners.clear()
            banners.addAll(it)
            showBannerLoading = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCategory().observeForever {
            categories.clear()
            categories.addAll(it)
            showCategoryLoading = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadAllFoods()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = { TopBar(navController) },
        bottomBar = { MyBottomBar(navController) },
        content = { innerPadding ->
            LazyColumn(
                state = listState,
                modifier = Modifier.padding(innerPadding)
            ) {
                item {
                    Banner(banners, showBannerLoading)
                }
                item {
                    Search(
                        onSearchTextChanged = { newText -> searchText = newText },
                        onSearchSubmit = {
                            scope.launch {
                                listState.animateScrollToItem(0)
                            }
                        }
                    )
                }
                item {
                    CategorySection(categories, showCategoryLoading, navController)
                }
                item {
                    Text(
                        text = if (searchText.isNotEmpty()) "Search results" else "List of dishes",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                itemsIndexed(filteredFoodList) { index, food ->
                    FoodItemRow(food = food, index = index) {
                        navController.navigate("detail/${food.Id}") // giống như ItemList
                    }
                    Divider()
                }

            }
        }
    )
}
