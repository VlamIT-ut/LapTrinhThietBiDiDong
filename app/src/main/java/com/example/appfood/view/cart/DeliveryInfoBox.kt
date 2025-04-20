package com.example.appfood.view.cart

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appfood.R
import com.example.appfood.model.data.repository.OrderRepository
import com.example.appfood.model.domain.OrderModel
import com.example.appfood.view.helper.ManagementCart
import com.example.appfood.viewModel.LocationViewModel

@Composable
fun DeliveryInfoBox(navController: NavController,
                    managementCart: ManagementCart,
                    tax: Double,
                    locationViewModel: LocationViewModel = viewModel()
                    ) {
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
            content = "Geocoder",
            icon = painterResource(id = R.drawable.location),
            navController = navController
        )
        // ✅ Hiển thị vị trí người dùng đã chọn (nếu có)
        locationViewModel.selectedPoint?.let { point ->
            Column(modifier = Modifier.padding(top = 4.dp, start = 40.dp)) {
                Text(
                    text = "Latitude: ${point.latitude}, Longitude: ${point.longitude}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                locationViewModel.selectedAddress?.let { address ->
                    Text(
                        text = address,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
        } ?: Text(
            text = "Location not saved on map",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, start = 40.dp)
        )


        Divider(modifier = Modifier.padding(vertical = 8.dp))
        PaymentSection(
            selectedMethod = selectedMethod,
            onMethodSelected = { selectedMethod = it },
            navController = navController
        )
    }

    val context = LocalContext.current
    val orderRepo = remember { OrderRepository() }

    Button(
        onClick = {
            val order = OrderModel(
                userId = "demo_user", // Cập nhật từ người dùng thật nếu có auth
                items = managementCart.getListCart(),
                total = managementCart.getTotalFee(),
                tax = tax,
                deliveryFee = 10.0,
                paymentMethod = selectedMethod,
                address = "Your address here",
                latitude = locationViewModel.selectedPoint?.latitude ?: 0.0,
                longitude = locationViewModel.selectedPoint?.longitude ?: 0.0,

            )

            orderRepo.saveOrder(order) { success ->
                if (success) {
                    Toast.makeText(context, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                    navController.navigate("success")
                } else {
                    Toast.makeText(context, "Failed to place order.", Toast.LENGTH_SHORT).show()
                }
            }
        },
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
                        navController.navigate("choose_location")
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
