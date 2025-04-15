package com.example.appfood.view.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R

@Composable
fun DeliveryInfoBox(navController: NavController) {
    var selectedMethod by rememberSaveable { mutableStateOf("Momo") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(color = colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        InfoItem(
            title = "Your Delivery Address",
            content = "NY-downtown-no97",
            icon = painterResource(id = R.drawable.location),
            navController = navController
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        PaymentSection(
            selectedMethod = selectedMethod,
            onMethodSelected = { selectedMethod = it },
            navController = navController
        )
    }

    Button(
        onClick = { navController.navigate("success") },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange)),
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text("Place Order", fontSize = 18.sp, color = Color.White)
    }
}

@Composable
fun InfoItem(title: String, content: String, icon: Painter, navController: NavController) {
    Column {
        Text(text = title, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (title != "Payment Method") {
                        navController.navigate("address")
                    }
                }
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = content,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PaymentSection(
    selectedMethod: String,
    onMethodSelected: (String) -> Unit,
    navController: NavController
) {
    Text(text = "Payment Method", fontSize = 14.sp, color = Color.Gray)
    Spacer(modifier = Modifier.height(4.dp))

    PaymentOptionRow(
        method = "Momo",
        selectedMethod = selectedMethod,
        onSelected = { onMethodSelected("Momo") },
        icon = painterResource(id = R.drawable.ic_momo),
        navController = navController
    )

    PaymentOptionRow(
        method = "VNPay",
        selectedMethod = selectedMethod,
        onSelected = { onMethodSelected("VNPay") },
        icon = painterResource(id = R.drawable.ic_vnpay),
        navController = navController
    )

    PaymentOptionRow(
        method = "Cash on Delivery",
        selectedMethod = selectedMethod,
        onSelected = { onMethodSelected("Cash on Delivery") },
        icon = painterResource(id = R.drawable.credit_card),
        navController = navController
    )
}

@Composable
fun PaymentOptionRow(
    method: String,
    selectedMethod: String,
    onSelected: () -> Unit,
    icon: Painter,
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelected()
                when (method) {
                    "VNPay" -> navController.navigate("mock_vnpay_payment")
                    "Momo" -> navController.navigate("mock_momo_login")
                }
            }
            .padding(vertical = 4.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = method,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = method,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = selectedMethod == method,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Green,
                unselectedColor = Color.Gray
            )
        )
    }
}
