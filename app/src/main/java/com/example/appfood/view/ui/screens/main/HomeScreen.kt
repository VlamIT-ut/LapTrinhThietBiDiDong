package com.example.appfood.view.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.appfood.model.domain.BannerModel
import com.example.appfood.model.domain.CategoryModel
import com.example.appfood.view.dashboard.CategorySection
import com.example.appfood.view.dashboard.Search
import com.example.appfood.view.navigation.MyBottomBar
import com.example.appfood.view.navigation.TopBar
import com.example.appfood.viewModel.MainViewModel
import com.example.appfood.view.dashboard.Banner

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = MainViewModel()
    val banners = remember { mutableStateListOf<BannerModel>() }
    val categories = remember { mutableStateListOf<CategoryModel>() }
    var showBannerLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }

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
    Scaffold(
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        topBar = { TopBar() },
        bottomBar = { MyBottomBar(navController) }, content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
            ) {

                item {
                    Banner(banners, showBannerLoading)
                }
                item {
                    Search()
                }
                item {
                    CategorySection(categories, showCategoryLoading,navController)
                }
            }
        }
    )

}
