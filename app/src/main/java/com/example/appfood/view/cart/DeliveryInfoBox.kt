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
import androidx.compose.ui.res.stringResource
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
import com.example.appfood.viewModel.NotificationViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.appfood.view.helper.formatAsString

// Thêm enum cho phương thức thanh toán
enum class PaymentMethod(val code: String) {
    MOMO("momo"),
    VNPAY("vnpay"),
    COD("cod")
}

@Composable
fun DeliveryInfoBox(
    navController: NavController,
    managementCart: ManagementCart,
    tax: Double,
    locationViewModel: LocationViewModel = viewModel(),
    notificationViewModel: NotificationViewModel = viewModel()
) {
    var selectedMethod by rememberSaveable { mutableStateOf(PaymentMethod.MOMO.code) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(color = colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        InfoItem(
            title = stringResource(R.string.your_delivery_address),
            content = stringResource(R.string.geocoder),
            icon = painterResource(id = R.drawable.location),
            navController = navController
        )

        // Hiển thị vị trí người dùng đã chọn
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
            stringResource(R.string.location_not),
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

    // Get string resources in Composable context
    val successTitle = stringResource(R.string.order_success_title)
    val successTime = stringResource(R.string.order_success_time)
    val successTotal = stringResource(R.string.order_success_total)
    val successAddress = stringResource(R.string.order_success_address)
    val successPayment = stringResource(R.string.order_success_payment)
    val successToast = stringResource(R.string.order_success_toast)
    val failedToast = stringResource(R.string.order_failed_toast)
    val loginToast = stringResource(R.string.login_required_toast)
    val locationNot = stringResource(R.string.location_not)
    val orderedFoodsLabel = stringResource(R.string.ordered_foods)

    Button(
        onClick = {
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            if (currentUserId != null) {
                val totalOrder = managementCart.getTotalFee() + tax + 10.0
                val order = OrderModel(
                    userId = currentUserId,
                    items = managementCart.getListCart(),
                    total = totalOrder,
                    tax = tax,
                    deliveryFee = 10.0,
                    paymentMethod = selectedMethod,
                    address = locationViewModel.selectedAddress ?: locationNot,
                    latitude = locationViewModel.selectedPoint?.latitude ?: 0.0,
                    longitude = locationViewModel.selectedPoint?.longitude ?: 0.0
                )

                orderRepo.saveOrder(order) { success ->
                    if (success) {
                        val orderTime = System.currentTimeMillis().formatAsString()
                        val foodNames = managementCart.getListCart().joinToString(separator = "\n- ") { it.Title }
                        val formattedTotal = String.format("%.2f", totalOrder)
                        val message = """
$successTitle
🕒 $orderTime
💰 ${successTotal.format(formattedTotal)}
📍 ${successAddress.format(locationViewModel.selectedAddress ?: locationNot)}
💳 ${successPayment.format(selectedMethod)}
🍽️ $orderedFoodsLabel:
- $foodNames
""".trimIndent()
                        notificationViewModel.addNotification(message)

                        Toast.makeText(context, successToast, Toast.LENGTH_SHORT).show()
                        navController.navigate("success")
                    }
                    else {
                        Toast.makeText(context, failedToast, Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(context, loginToast, Toast.LENGTH_SHORT).show()
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange)),
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(stringResource(R.string.place_order), fontSize = 18.sp, color = Color.White)
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
    Text(text = stringResource(R.string.payment_method), fontSize = 14.sp, color = Color.Gray)
    Spacer(modifier = Modifier.height(4.dp))

    PaymentOptionRow(
        methodCode = PaymentMethod.MOMO.code,
        methodLabel = "Momo",
        selectedMethod = selectedMethod,
        onSelected = onMethodSelected,
        icon = painterResource(id = R.drawable.ic_momo),
        navController = navController
    )

    PaymentOptionRow(
        methodCode = PaymentMethod.VNPAY.code,
        methodLabel = "VNPay",
        selectedMethod = selectedMethod,
        onSelected = onMethodSelected,
        icon = painterResource(id = R.drawable.ic_vnpay),
        navController = navController
    )

    PaymentOptionRow(
        methodCode = PaymentMethod.COD.code,
        methodLabel = stringResource(R.string.cash_on_delivery),
        selectedMethod = selectedMethod,
        onSelected = onMethodSelected,
        icon = painterResource(id = R.drawable.credit_card),
        navController = navController
    )
}

@Composable
fun PaymentOptionRow(
    methodCode: String,
    methodLabel: String,
    selectedMethod: String,
    onSelected: (String) -> Unit,
    icon: Painter,
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelected(methodCode)
                when (methodCode) {
                    PaymentMethod.VNPAY.code -> navController.navigate("mock_vnpay_payment")
                    PaymentMethod.MOMO.code -> navController.navigate("mock_momo_login")
                }
            }
            .padding(vertical = 4.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = methodLabel,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = methodLabel,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = selectedMethod == methodCode,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Green,
                unselectedColor = Color.Gray
            )
        )
    }
}