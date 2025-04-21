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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.appfood.model.domain.BannerModel
import com.example.appfood.model.domain.CategoryModel
import com.example.appfood.view.dashboard.CategorySection
import com.example.appfood.view.dashboard.Search
import com.example.appfood.view.navigation.MyBottomBar
import com.example.appfood.view.navigation.TopBar
import com.example.appfood.viewModel.MainViewModel
import com.example.appfood.view.dashboard.Banner
import com.example.appfood.viewModel.NotificationViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfood.model.data.local.UserPreferences

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = MainViewModel(userPreferences = UserPreferences(LocalContext.current))
    val banners = remember { mutableStateListOf<BannerModel>() }
    val categories = remember { mutableStateListOf<CategoryModel>() }
    var showBannerLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }
    val notificationViewModel: NotificationViewModel = viewModel()

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
        topBar = { TopBar(navController, notificationViewModel ) },
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
