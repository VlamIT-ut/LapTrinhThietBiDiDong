package com.example.appfood.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appfood.view.ui.screens.LoginScreen
import com.example.appfood.view.ui.screens.ProfileScreen
import com.example.appfood.view.ui.screens.SignUpScreen
import com.example.appfood.view.ui.screens.WelcomeScreen
import com.example.appfood.viewModel.AuthViewModel

@Composable
fun AppNavigation(viewModel: AuthViewModel?) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController, viewModel ?: return@composable) }
        composable("sign_up") { SignUpScreen(navController, viewModel ?: return@composable) }
        composable("profile") { ProfileScreen(navController, viewModel ?: return@composable) }
    }
}