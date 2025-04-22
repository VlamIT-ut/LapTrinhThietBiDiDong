package com.example.appfood.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.appfood.R
import com.example.appfood.model.domain.FoodModel

@Composable
fun FoodItemRow(food: FoodModel, index: Int = 0, onItemClick: (FoodModel) -> Unit) {
    val isEvenRow = index % 2 == 0

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .height(120.dp)
            .background(colorResource(id = R.color.grey), shape = RoundedCornerShape(10.dp))
            .clickable { onItemClick(food) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEvenRow) {
            FoodImage(food)
            FoodDetails(food)
        } else {
            FoodDetails(food)
            FoodImage(food)
        }
    }
}

@Composable
fun FoodImage(food: FoodModel) {
    AsyncImage(
        model = food.ImagePath,
        contentDescription = food.Title,
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.grey)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RowScope.FoodDetails(food: FoodModel) {
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxHeight()
            .weight(1f)
    ) {
        Text(
            text = food.Title,
            color = colorResource(R.color.darkPurple),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
        TimingRow(food.TimeValue)
        RatingBarRow(food.Star)
        Text(
            text = "$${food.Price}",
            color = colorResource(R.color.darkPurple),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun RatingBarRow(star: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.star),
            contentDescription = null,
            modifier = Modifier.padding(end = 6.dp)
        )
        Text(text = "$star", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun TimingRow(timeValue: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.time),
            contentDescription = null,
            modifier = Modifier.padding(end = 6.dp)
        )
        Text(text = "$timeValue min", style = MaterialTheme.typography.bodyMedium)
    }
}
