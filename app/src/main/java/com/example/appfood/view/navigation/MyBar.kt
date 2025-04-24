package com.example.appfood.view.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.appfood.R
import com.google.accompanist.insets.ui.BottomNavigation

@Composable
fun MyBottomBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val items = listOf(
        Triple("home", R.drawable.btn_1, "Home"),
        Triple("cart_screen", R.drawable.btn_2, "Cart"),
        Triple("favorite", R.drawable.btn_3, "Favorite"),
        Triple("my_orders", R.drawable.btn_4, "Orders"),
        Triple("profile", R.drawable.btn_5, "Profile")
    )

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.grey),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { (route, icon, desc) ->
                val isSelected = currentRoute == route
                val bgColor = if (isSelected) Color.White else colorResource(id = R.color.grey)
                val iconColor = if (isSelected) colorResource(id = R.color.orange) else Color.Black

                Button(
                    onClick = {
                        if (!isSelected) navController.navigate(route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = bgColor,
                        contentColor = iconColor
                    )
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = desc,
                        modifier = Modifier.size(32.dp),
                        tint = iconColor
                    )
                }
            }
        }
    }
}
