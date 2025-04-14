package com.example.appfood.view.ui.screens.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R

@Composable
fun PaymentScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp) // Lùi nội dung xuống một chút
    ) {
        // Header with back arrow and title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 24.dp),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFFF5A00),
                modifier = Modifier
                    .size(32.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Payments",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Payment options
        PaymentOption(
            logoRes = R.drawable.ic_momo,
            name = "Momo",
            onClick = {
                Toast.makeText(context, "Momo clicked", Toast.LENGTH_SHORT).show()
                // TODO: Thêm tích hợp Momo tại đây
            }
        )
        Spacer(modifier = Modifier.height(12.dp))

        PaymentOption(
            logoRes = R.drawable.ic_vnpay,
            name = "VNPay",
            onClick = {
                Toast.makeText(context, "VNPay clicked", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun PaymentOption(
    logoRes: Int,
    name: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(vertical = 16.dp)
    ) {
        // Icon bên trái
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = name,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        )

        // Text ở giữa block
        Text(
            text = name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
