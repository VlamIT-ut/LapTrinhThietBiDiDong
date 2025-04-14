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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appfood.R
import com.google.accompanist.insets.ui.BottomNavigation

/**
 * `MyBottomBar` is a Composable function that creates a custom bottom navigation bar.
 *
 * It displays five icons representing different sections of the application:
 * Home, Shopping Cart, Favorite, Order, and Profile.
 *
 * The bar is positioned at the bottom of the screen and provides navigation
 * between these sections using the provided [navController].
 *
 * @param navController The NavController instance used for navigating between different screens.
 *
 * The bottom bar's features include:
 * - **Navigation:** Clicking on each icon navigates the user to the corresponding screen.
 *   - "Home" navigates to the "home" route.
 *   - "Shopping Cart" navigates to the "cart_screen" route, keeping the history.
 *   - "Profile" navigates to the "profile" route.
 * - **Styling:** The bar fills the width of the screen, has a background color of gray, and a slight elevation.
 * - **System Bar Padding:** It utilizes `navigationBarsPadding` to avoid overlapping with the system navigation bar.
 * - **Icon size**: Icon size is set to 32.dp
 *
 * The icons are:
 * - R.drawable.btn_1: Home
 * - R.drawable.btn_2: Shopping Cart
 * - R.drawable.btn_3: Favorite
 * - R.drawable.btn_4: Order
 * - R.drawable.btn_5: Profile
 */
@Composable
fun MyBottomBar(navController: NavController) {
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(), // Đẩy lên khỏi thanh điều hướng của hệ thống
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.grey),
    ) {
        // Sử dụng Row để căn giữa các biểu tượng
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly // Căn đều các biểu tượng
        ) {
            Button(onClick = {navController.navigate("home")},
                modifier = Modifier.align(Alignment.CenterVertically),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.grey),contentColor = Color.Black)) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_1),
                    contentDescription = "Home",
                    modifier = Modifier.size(32.dp))
            }
            Button(onClick = { navController.navigate("cart_screen") {
                launchSingleTop = true
            }},
                modifier = Modifier.align(Alignment.CenterVertically),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.grey), contentColor = Color.Black)) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_2),
                    contentDescription = "Shopping Cart",
                    modifier = Modifier.size(32.dp)
                )
            }
            Button(onClick = {navController.navigate("favorite")},
                modifier = Modifier.align(Alignment.CenterVertically),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.grey),contentColor = Color.Black)) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_3),
                    contentDescription = "Favorite",
                    modifier = Modifier.size(32.dp)
                )
            }
            Button(onClick = {},
                modifier = Modifier.align(Alignment.CenterVertically),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.grey),contentColor = Color.Black)
                ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_4),
                    contentDescription = "Order",
                    modifier = Modifier.size(32.dp)
                )
            }
            Button(onClick = {navController.navigate("profile")},
                modifier = Modifier.align(Alignment.CenterVertically),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.grey),contentColor = Color.Black)) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_5),
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}