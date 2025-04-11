package com.example.appfood.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appfood.view.ui.screens.main.HomeScreen
import com.example.appfood.view.ui.screens.login_signup.LoginScreen
import com.example.appfood.view.ui.screens.main.ProfileScreen
import com.example.appfood.view.ui.screens.login_signup.SignUpScreen
import com.example.appfood.view.ui.screens.main.CartScreen
import com.example.appfood.view.ui.screens.splash.GetStartedScreen1
import com.example.appfood.view.ui.screens.splash.GetStartedScreen2
import com.example.appfood.view.ui.screens.splash.GetStartedScreen3
import com.example.appfood.view.ui.screens.splash.SplashScreen
import com.example.appfood.viewModel.AuthViewModel

@Composable
fun AppNavigation(viewModel: AuthViewModel?) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { SplashScreen(navController) }
        composable("get_started_1") { GetStartedScreen1(navController) }
        composable("get_started_2") { GetStartedScreen2(navController) }
        composable("get_started_3") { GetStartedScreen3(navController) }
        composable("login") { LoginScreen(navController, viewModel ?: return@composable) }
        composable("sign_up") { SignUpScreen(navController, viewModel ?: return@composable) }
        composable("home") { HomeScreen(navController) }
        composable("cart_screen") {
            CartScreen(
                navController = navController
            )
        }
        composable("profile") { ProfileScreen(navController, viewModel ?: return@composable) }
    }
}