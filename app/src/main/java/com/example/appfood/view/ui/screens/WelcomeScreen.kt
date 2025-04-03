package com.example.appfood.view.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavController){
    LaunchedEffect(Unit) {
        delay(10000) // 10 s
        navController.navigate("login")
    }
    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text("Welcome to", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
        Text("Food Delivery App", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.Red)
        Image(
            painterResource(id= R.drawable.logo),contentDescription = null,
            modifier = Modifier.size(272.dp,257.dp))
    }
}