package com.example.appfood.view.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        topBar = {TopAppBar(title = { Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Image(
                    painterResource(R.drawable.back_grey),
                    contentDescription = null,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
                Text(
                    text = "About US",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.orange),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(48.dp))
            }

        }
        },
            backgroundColor = colorResource(R.color.white)
        ) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "About Us",
                color = Color.Red,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome to Food Delivery – your go-to food delivery app for fast, convenient, and reliable service! " +
                        "Our mission is to connect you with the best restaurants, eateries, and street food vendors in town. " +
                        "Whether you’re craving a quick snack, a hearty dinner, or a refreshing drink, Food Delivery is here to deliver.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Review Application",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Star, contentDescription = "Star", tint = Color.Red)
            }
        }
    }
}
