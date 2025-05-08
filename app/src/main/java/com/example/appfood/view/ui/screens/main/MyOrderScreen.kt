package com.example.appfood.view.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appfood.R
import com.example.appfood.model.domain.OrderModel
import com.example.appfood.view.navigation.MyBottomBar
import com.example.appfood.viewModel.OrderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyOrderScreen(
    viewModel: OrderViewModel = viewModel(),
    navController: NavController
) {
    val orders by viewModel.orders.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOrders()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            com.google.accompanist.insets.ui.TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painterResource(R.drawable.back_grey),
                                contentDescription = null,
                                modifier = Modifier.clickable { navController.popBackStack() }
                            )
                            Text(
                                stringResource(R.string.my_order),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.orange),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(48.dp))
                        }
                    }
                },
                backgroundColor = colorResource(R.color.white)
            )
        },
        bottomBar = { MyBottomBar(navController) }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            if (orders.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.no_order_items))
                    }
                }
            } else {
                items(orders) { order ->
                    OrderItem(order = order, navController = navController)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: OrderModel, navController: NavController) {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val dateString = formatter.format(Date(order.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Order ID: ${order.orderId}", fontWeight = FontWeight.Bold)
            Text("Total: $${order.total}", color = Color.DarkGray)
            Text("Payment: ${order.paymentMethod}")
            Text("Address: ${order.address}")
            Text("Time: $dateString", style = MaterialTheme.typography.labelSmall)

            if (order.items.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Items:", fontWeight = FontWeight.Medium)
                order.items.forEach { item ->
                    Text("- ${item.Title} x${item.numberInCart}")
                }
            }

            if (!order.isCancelled) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            navController.navigate("cancel_order/${order.orderId}")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(stringResource(R.string.canceled_order), color = Color.White)
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    stringResource(R.string.order_canceled),
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
