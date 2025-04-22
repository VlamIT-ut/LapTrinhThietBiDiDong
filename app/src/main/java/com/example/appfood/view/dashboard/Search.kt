package com.example.appfood.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.appfood.R

@Composable
fun Search(
    onSearchTextChanged: (String) -> Unit,
    onSearchSubmit: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current // Thêm focus manager

    TextField(
        value = text,
        onValueChange = {
            text = it
            onSearchTextChanged(it)
        },
        label = {
            Text(
                text = "What would you like to eat",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
        },
        trailingIcon = {
            Image(
                painter = painterResource(R.drawable.search),
                contentDescription = null,
                modifier = Modifier.size(22.dp).
                clickable {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onSearchSubmit()
                }
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Blue,
            focusedIndicatorColor = Color.Green,
            unfocusedIndicatorColor = Color.Black,
            disabledIndicatorColor = Color.Gray,
            focusedLabelColor = Color.Red,
            focusedContainerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus() // Thoát khỏi focus của TextField
                onSearchSubmit()          // Gọi hàm callback từ HomeScreen (cuộn lên đầu, v.v.)
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
            .background(colorResource(R.color.grey), CircleShape)
    )
}
