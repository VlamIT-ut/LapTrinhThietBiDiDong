package com.example.appfood.view.navigation

import com.example.appfood.view.ui.screens.map.DeliveryLocationScreen
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
import com.example.appfood.view.helper.ManagementCart
import com.example.appfood.view.notification.NotificationScreen
import com.example.appfood.view.ui.screens.main.ItemsListScreen
import com.example.appfood.view.ui.screens.main.HomeScreen
import com.example.appfood.view.ui.screens.login_signup.LoginScreen
import com.example.appfood.view.ui.screens.main.ProfileScreen
import com.example.appfood.view.ui.screens.login_signup.SignUpScreen
import com.example.appfood.view.ui.screens.main.AboutScreen
import com.example.appfood.view.ui.screens.main.CartScreen
import com.example.appfood.view.ui.screens.main.DetailFoodScreen
import com.example.appfood.view.ui.screens.main.FavoriteScreen
import com.example.appfood.view.ui.screens.main.HelpAndFaqScreen
import com.example.appfood.view.ui.screens.main.MyOrderScreen
import com.example.appfood.view.ui.screens.main.SettingsScreen
import com.example.appfood.view.ui.screens.main.SuccessScreen
import com.example.appfood.view.ui.screens.payment.MockMomoLoginScreen
import com.example.appfood.view.ui.screens.payment.MockVnpayPayment
import com.example.appfood.view.ui.screens.splash.GetStartedScreen1
import com.example.appfood.view.ui.screens.splash.GetStartedScreen2
import com.example.appfood.view.ui.screens.splash.GetStartedScreen3
import com.example.appfood.view.ui.screens.splash.SplashScreen
import com.example.appfood.viewModel.AuthViewModel
import com.example.appfood.viewModel.LocationViewModel
import com.example.appfood.viewModel.MainViewModel
import com.example.appfood.viewModel.NotificationViewModel
import com.example.appfood.viewModel.OrderViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppNavigation(authViewModel: AuthViewModel,mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val locationViewModel = LocationViewModel()
    val orderViewModel = OrderViewModel()
    val isLoggedIn = authViewModel.isLoggedIn.collectAsState(initial = false).value
    val isFirstLaunch = authViewModel.isFirstLaunch.collectAsState(initial = true).value
    val startDestination = if (isFirstLaunch) "welcome" else if (isLoggedIn) "home" else "login"
    val notificationViewModel: NotificationViewModel = viewModel()


    NavHost(navController = navController,startDestination = startDestination ) {
        composable("welcome") {
            // Màn Splash chỉ hiển thị lần đầu
            SplashScreen(navController)
            LaunchedEffect(Unit) {
                authViewModel.completeFirstLaunch()  // Cập nhật không còn là lần đầu
            }
        }
        composable("get_started_1") { GetStartedScreen1(navController) }
        composable("get_started_2") { GetStartedScreen2(navController) }
        composable("get_started_3") { GetStartedScreen3(navController) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("sign_up") { SignUpScreen(navController, authViewModel) }
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
                navController = navController,
                managementCart = ManagementCart(context = LocalContext.current),
                locationViewModel,notificationViewModel)
        }
        composable("detail/{foodId}") { backStackEntry ->
            val foodId = backStackEntry.arguments?.getString("foodId") ?: ""
            val food by mainViewModel.loadFoodDetail(foodId).observeAsState()

            if (food != null) {
                DetailFoodScreen(
                    item = food!!,
                    onBackClick = { navController.popBackStack() },
                    onAddToCartClick = { /* Optional */ },
                    viewModel = mainViewModel
                )
            } else {
                Text("Loading...")
            }
        }

        composable("profile") { ProfileScreen(navController, authViewModel) }
        composable("mock_momo_login") {
            MockMomoLoginScreen(navController)
        }
        composable("mock_vnpay_payment"){
            MockVnpayPayment(navController)
        }
        composable("favorite") {
            FavoriteScreen(navController = navController, viewModel = mainViewModel)
        }
        composable("choose_location") {
            DeliveryLocationScreen(navController, locationViewModel)
        }
        composable("success"){ SuccessScreen(navController) }
        composable("my_orders") {
            MyOrderScreen(orderViewModel,navController)
        }
        composable("settings") {
            SettingsScreen(
                navController = navController,
                currentLang = mainViewModel.appLanguage.collectAsState().value,
                onLanguageToggle = { mainViewModel.toggleLanguage() },
                onAccountDeleteClick = { /*...*/ }
            )
        }

        composable("about") { AboutScreen(navController) }
        composable("help"){ HelpAndFaqScreen(navController) }
        composable("notification"){ NotificationScreen(navController,notificationViewModel) }
    }

}