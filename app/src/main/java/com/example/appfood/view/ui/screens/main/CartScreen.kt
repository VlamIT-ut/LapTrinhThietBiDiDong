package com.example.appfood.view.ui.screens.main


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.example.appfood.R
import com.example.appfood.view.cart.CartItem
import com.example.appfood.view.cart.CartSummary
import com.example.appfood.view.cart.DeliveryInfoBox
import com.example.appfood.view.helper.ManagementCart
import com.example.appfood.view.navigation.MyBottomBar
import com.example.appfood.viewModel.LocationViewModel
import com.example.appfood.viewModel.NotificationViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import java.util.ArrayList
/**
 * [CartScreen] composable function displays the user's shopping cart.
 *
 * This screen shows a list of items added to the cart, allows for quantity adjustments,
 * calculates the total price (including tax and delivery), and displays order summary and delivery info.
 *
 * @param navController The navigation controller used to navigate between screens.
 * @param managementCart The cart management object responsible for handling cart operations.
 *                       Defaults to a new `ManagmentCart` instance.
 *
 * @see ManagementCart
 * @see CartItem
 * @see CartSummary
 * @see DeliveryInfoBox
 * @see MyBottomBar
 * @see calculatorCart
 */
@SuppressLint("MutableCollectionMutableState")
@Composable
fun CartScreen(navController: NavController,
               managementCart: ManagementCart,
               locationViewModel: LocationViewModel,
               notificationViewModel: NotificationViewModel // thêm dòng này
) {
    val cartItems = remember { mutableStateOf(managementCart.getListCart()) }
    val tax = remember { mutableDoubleStateOf(value = 0.0) }
    calculatorCart(managementCart, tax)

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
                    stringResource(R.string.cart),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.orange),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(48.dp))
            }

        }
        },
            backgroundColor = Color.White) },
        bottomBar = { MyBottomBar(navController) },
        content = { innerPadding ->
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
            ) {
                if (cartItems.value.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(R.string.no_cart_items))
                        }
                    }
                } else {
                    items(cartItems.value) { item ->
                        CartItem(
                            cartItems = cartItems.value,
                            item = item,
                            managmentCart = managementCart,
                            onItemChange = {
                                calculatorCart(managementCart, tax)
                                cartItems.value = ArrayList(managementCart.getListCart())
                            }
                        )
                    }
                    item {
                        Text(
                            text = stringResource(R.string.order_summary),
                            color = colorResource(R.color.darkPurple),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    item {
                        CartSummary(
                            itemTotal = managementCart.getTotalFee(),
                            tax = tax.doubleValue,
                            delivery = 10.0
                        )
                    }
                    item {
                        Text(
                            text = stringResource(R.string.information),
                            color = colorResource(R.color.darkPurple),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    item {
                        DeliveryInfoBox(navController = navController,
                            managementCart = managementCart,
                            tax = tax.doubleValue,
                            locationViewModel = locationViewModel,
                            notificationViewModel=notificationViewModel)
                    }
                }
            }
        }
    )
}
fun calculatorCart(managementCart: ManagementCart,tax:MutableState<Double>){
    val percentTax = 0.02
    tax.value = Math.round((managementCart.getTotalFee()*percentTax)*100)/100.0
}