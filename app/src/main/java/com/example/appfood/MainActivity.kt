package com.example.appfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appfood.ui.theme.AppFoodTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFoodTheme {
                AppNavigation()
            }
        }
    }
}
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home"){
        composable("home"){WelcomeScreen(navController)}
        composable("login"){LoginScreen(navController)}
    }
    }

@Composable
fun WelcomeScreen(navController: NavController){
    LaunchedEffect(Unit) {
        delay(10000) // 10 giây
        navController.navigate("login")
    }
    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(painterResource(id=R.drawable.logo),contentDescription = null,
            modifier = Modifier.size(272.dp,257.dp))
    }
}
@Composable
fun LoginScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.height(117.dp))
        Text("Welcome to", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
        Text("Food Delivery App", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.Red)
        Spacer(modifier = Modifier.height(126.dp))
        Text("Sign in", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
        Spacer(modifier = Modifier.height(125.dp))
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
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.size(367.dp,80.dp).padding(8.dp),
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(8.dp),
            )

            {
                Icon(imageVector = Icons.Default.Email, contentDescription = "email",
                    modifier = Modifier.weight(1f).size(32.dp), tint = Color.Black)
                Text("Continue with Email", fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold, color = Color.Black,modifier = Modifier.weight(4f))
            }

        }
    }

}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFoodTheme {
        AppNavigation()
    }
}