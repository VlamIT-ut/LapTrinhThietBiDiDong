package com.example.appfood.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appfood.view.ui.screens.LoginScreen
import com.example.appfood.view.ui.screens.SignUpScreen
import com.example.appfood.view.ui.screens.WelcomeScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home"){
        composable("home"){ WelcomeScreen(navController) }
        composable("login"){ LoginScreen(navController) }
        composable("sign_up"){ SignUpScreen(navController) }
    }
}