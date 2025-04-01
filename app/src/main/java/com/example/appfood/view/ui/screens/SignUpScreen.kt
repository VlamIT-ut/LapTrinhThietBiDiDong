package com.example.appfood.view.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R

@Composable
fun SignUpScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.height(138.dp))
        Text("Sign up to Food Delivery", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
        Spacer(modifier = Modifier.height(80.dp))
        Column(verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,){

            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.size(367.dp,80.dp).padding(8.dp),
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(8.dp),)
            {
                Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center) {
                    Image(painterResource(id = R.drawable.logo_google), contentDescription = null,modifier = Modifier.weight(1f))
                    Text("Continue with Google", fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold, color = Color.Black,modifier = Modifier.weight(4f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.size(367.dp,80.dp).padding(8.dp),
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(8.dp),)
            {
                Image(painterResource(id = R.drawable.apple_logo), contentDescription = null,modifier = Modifier.weight(1f))
                Text("Continue with Apple", fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold, color = Color.Black,modifier = Modifier.weight(4f))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("or", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            var name by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var isShowPassword by remember { mutableStateOf(false) }

            Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center)
            {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Email") },
                    placeholder = { Text("Enter email") },
                    modifier = Modifier.fillMaxWidth().size(335.dp,60.dp),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null)},
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text("Password")},
                    placeholder = { Text("Enter Password")},
                    modifier = Modifier.fillMaxWidth().size(335.dp,60.dp),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null)},

                    trailingIcon = {
                        IconButton(onClick = {isShowPassword =!isShowPassword}
                        ) {
                            Icon(
                                if(isShowPassword) Icons.Filled.AccountCircle else Icons.Filled.Person
                                , contentDescription = null
                            )

                        }
                    },
                    visualTransformation = if(isShowPassword) VisualTransformation.None else PasswordVisualTransformation()
                )
                Text("Forgot Password?", textAlign = TextAlign.Right)
                Button(onClick = {},
                    modifier = Modifier.size(367.dp,80.dp).padding(8.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(12.dp)
                    ,colors = ButtonDefaults.buttonColors(Color.Blue)) {
                    Text("Continue")
                }
            }
            Text("Already have a Food Delivery account ?", textAlign = TextAlign.Right)
            Button(onClick = {navController.navigate("login")},
                modifier = Modifier.size(367.dp,80.dp).padding(8.dp).align(Alignment.CenterHorizontally),
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(12.dp)
                ,colors = ButtonDefaults.buttonColors(Color.White)) {
                Text("Login",color = Color.Black,fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}