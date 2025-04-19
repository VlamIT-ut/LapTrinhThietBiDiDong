package com.example.appfood.view.cart

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource // <-- Đã thêm: dùng cho icon xoá
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.appfood.R
import com.example.appfood.model.domain.FoodModel
import com.example.appfood.view.helper.ManagmentCart
import java.util.ArrayList

@Composable
fun CartItem(
    cartItems: ArrayList<FoodModel>,
    item: FoodModel,
    managmentCart: ManagmentCart,
    onItemChange: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .border(1.dp, colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
    ) {
        // ⬇️ Thêm tham chiếu deleteBtn
        val (pic, titleTxt, feeEachTime, totalEachItem, quantity, deleteBtn) = createRefs()

        var numberInCart by remember { mutableIntStateOf(item.numberInCart) }
        val decimalFormat = DecimalFormat("#,##0.00")

        Image(
            painter = rememberAsyncImagePainter(item.ImagePath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(135.dp)
                .height(100.dp)
                .background(
                    colorResource(R.color.grey),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(pic) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = item.Title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .constrainAs(titleTxt) {
                    start.linkTo(pic.end)
                    top.linkTo(pic.top)
                }
                .padding(start = 8.dp, top = 8.dp)
        )

        Text(
            text = "$${decimalFormat.format(item.Price)}",
            fontSize = 16.sp,
            color = colorResource(R.color.darkPurple),
            modifier = Modifier
                .constrainAs(feeEachTime) {
                    start.linkTo(titleTxt.start)
                    top.linkTo(titleTxt.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 8.dp)
        )

        Text(
            text = "$${decimalFormat.format(numberInCart * item.Price)}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(totalEachItem) {
                    end.linkTo(parent.end)
                    bottom.linkTo(pic.bottom)
                }
                .padding(8.dp)
        )

        ConstraintLayout(
            modifier = Modifier
                .width(100.dp)
                .padding(start = 8.dp)
                .constrainAs(quantity) {
                    start.linkTo(titleTxt.start)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            val (plusCartBtn, minusCartBtn, numberItemText) = createRefs()

            Box(modifier = Modifier
                .padding(2.dp)
                .size(28.dp)
                .constrainAs(minusCartBtn) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {
                    if (numberInCart > 1) {
                        managmentCart.minusItem(cartItems, cartItems.indexOf(item)) { onItemChange() }
                        numberInCart--
                        item.numberInCart = numberInCart
                    }
                }
            ) {
                Text(
                    text = "-",
                    color = colorResource(R.color.orange),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = item.numberInCart.toString(),
                color = colorResource(R.color.darkPurple),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(numberItemText) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            Box(modifier = Modifier
                .padding(2.dp)
                .size(28.dp)
                .constrainAs(plusCartBtn) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {
                    managmentCart.plusItem(cartItems, cartItems.indexOf(item)) { onItemChange() }
                    numberInCart++
                    item.numberInCart = numberInCart
                }
            ) {
                Text(
                    text = "+",
                    color = colorResource(R.color.orange),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }

        // ⬇️ Nút xoá món - ĐÃ THÊM
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .constrainAs(deleteBtn) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .clickable {
                    managmentCart.removeItem(cartItems, cartItems.indexOf(item)) {
                        onItemChange()
                    }
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.i_delete), // đảm bảo bạn có ic_delete trong drawable
                contentDescription = "Delete",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
