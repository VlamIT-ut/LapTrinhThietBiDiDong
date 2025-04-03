package com.example.appfood.view.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appfood.R
import com.example.appfood.viewModel.AuthState
import com.example.appfood.viewModel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: AuthViewModel) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val user = (authState as? AuthState.Success)?.user

   Scaffold(modifier = Modifier.fillMaxSize().padding(16.dp)) {paddingValues ->
       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(paddingValues),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.SpaceEvenly
       ) {
           // User Info
           UserInfoSection(user)

           Spacer(modifier = Modifier.height(32.dp))

           // Menu Items
           ProfileMenuItem(iconRes = R.drawable.ic_profile, title = "Profile") {
               // Điều hướng đến màn hình Profile chi tiết (nếu có)
           }
           ProfileMenuItem(iconRes = R.drawable.ic_my_orders, title = "My Orders") {
               // Điều hướng đến màn hình My Orders
           }
           ProfileMenuItem(iconRes = R.drawable.ic_delivery_address, title = "Delivery Address") {
               // Điều hướng đến màn hình Delivery Address
           }
           ProfileMenuItem(iconRes = R.drawable.ic_payments, title = "Payments") {
               // Điều hướng đến màn hình Payments
           }
           ProfileMenuItem(iconRes = R.drawable.ic_contact_us, title = "Contact US") {
               // Điều hướng đến màn hình Contact US
           }
           ProfileMenuItem(iconRes = R.drawable.ic_setting, title = "Setting") {
               // Điều hướng đến màn hình Setting
           }
           ProfileMenuItem(iconRes = R.drawable.ic_help_faq, title = "Help & FAQ") {
               // Điều hướng đến màn hình Help & FAQ
           }
           ProfileMenuItem(iconRes = R.drawable.ic_logout, title = "Log out", iconTint = Color.Red) {
               viewModel.signOut()
               navController.navigate("welcome") {
                   popUpTo("home") { inclusive = true }
               }
           }

           Spacer(modifier = Modifier.weight(1f))

           // Bottom Navigation (Mock)
           BottomNavigationBar()
       }
   }
}

@Composable
fun UserInfoSection(user: com.google.firebase.auth.FirebaseUser?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Hiển thị ảnh đại diện
        if (user?.photoUrl != null) {
            AsyncImage(
                model = user.photoUrl.toString(),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                error = painterResource(id = R.drawable.ic_profile_placeholder)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = user?.displayName ?: "Lam Nguyen",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = user?.email ?: "ut.edu.vn",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ProfileMenuItem(iconRes: Int, title: String, iconTint: Color = Color(0xFFFF5722), onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconTint)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = if (title == "Log out") Color.Red else Color.Black
        )
    }
}

@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "Home",
            modifier = Modifier.size(32.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_shopping_cart),
            contentDescription = "Shopping Cart",
            modifier = Modifier.size(32.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            modifier = Modifier.size(32.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_favorite),
            contentDescription = "Favorite",
            modifier = Modifier.size(32.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_profile__2_),
            contentDescription = "Profile",
            modifier = Modifier.size(32.dp),
        )
    }
}