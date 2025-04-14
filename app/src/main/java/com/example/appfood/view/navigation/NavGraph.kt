package com.example.appfood.view.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appfood.view.helper.ManagmentCart
import com.example.appfood.view.ui.screens.main.ItemsListScreen
import com.example.appfood.view.ui.screens.main.HomeScreen
import com.example.appfood.view.ui.screens.login_signup.LoginScreen
import com.example.appfood.view.ui.screens.main.ProfileScreen
import com.example.appfood.view.ui.screens.login_signup.SignUpScreen
import com.example.appfood.view.ui.screens.main.CartScreen
import com.example.appfood.view.ui.screens.main.DetailFoodScreen
import com.example.appfood.view.ui.screens.main.FavoriteScreen
import com.example.appfood.view.ui.screens.payment.MockMomoLoginScreen
import com.example.appfood.view.ui.screens.splash.GetStartedScreen1
import com.example.appfood.view.ui.screens.splash.GetStartedScreen2
import com.example.appfood.view.ui.screens.splash.GetStartedScreen3
import com.example.appfood.view.ui.screens.splash.SplashScreen
import com.example.appfood.viewModel.AuthViewModel
import com.example.appfood.viewModel.MainViewModel

@Composable
fun AppNavigation(viewModel: AuthViewModel?) {
    val navController = rememberNavController()
    val mainViewModel = MainViewModel()
    val isLoggedIn = viewModel?.isLoggedIn?.collectAsState(initial = false)?.value ?: false
    val isFirstLaunch = viewModel?.isFirstLaunch?.collectAsState(initial = true)?.value ?: true
    val startDestination = if (isFirstLaunch) "welcome" else if (isLoggedIn) "home" else "login"

    NavHost(navController = navController,startDestination = startDestination ) {
        composable("welcome") {
            // Màn Splash chỉ hiển thị lần đầu
            SplashScreen(navController)
            LaunchedEffect(Unit) {
                viewModel?.completeFirstLaunch()  // Cập nhật không còn là lần đầu
            }
        }
        composable("get_started_1") { GetStartedScreen1(navController) }
        composable("get_started_2") { GetStartedScreen2(navController) }
        composable("get_started_3") { GetStartedScreen3(navController) }
        composable("login") { LoginScreen(navController, viewModel ?: return@composable) }
        composable("sign_up") { SignUpScreen(navController, viewModel ?: return@composable) }
        composable("home") { HomeScreen(navController) }
        composable("items_list/{id}/{title}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""

            // Đảm bảo bạn lấy đúng ViewModel cho composable này

            ItemsListScreen(
                title = title,
                navController = navController,
                viewModel = mainViewModel,
                id = id,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("cart_screen") {
            CartScreen(
                navController = navController, managementCart = ManagmentCart(context = LocalContext.current)
            )
        }
        composable("detail/{foodId}") { backStackEntry ->
            val foodId = backStackEntry.arguments?.getString("foodId") ?: ""
            val food by mainViewModel.loadFoodDetail(foodId).observeAsState()

            if (food != null) {
                DetailFoodScreen(
                    item = food!!,
                    onBackClick = { navController.popBackStack() },
                    onAddToCartClick = { /* Optional */ }
                )
            } else {
                Text("Loading...")
            }
        }

        composable("profile") { ProfileScreen(navController, viewModel ?: return@composable) }
        composable("mock_momo_login") {
            MockMomoLoginScreen(navController)
        }
        composable("favorite") {FavoriteScreen(navController)}
    }
}