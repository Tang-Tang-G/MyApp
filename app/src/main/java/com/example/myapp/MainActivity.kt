package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.model.InitModel
import com.example.myapp.screens.Content
import com.example.myapp.screens.login.ForgetPassword
import com.example.myapp.screens.login.Login
import com.example.myapp.screens.login.SignUp
import com.example.myapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitModel()
            MyAppTheme {
                MainView()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainView() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "content",
    ) {
        composable("login") {
            Login(navController)
        }
        composable("content") {
            Content(navController)
        }
        composable("signUp"){
            SignUp(navController)
        }
        composable("ForgetPassword"){
            ForgetPassword(navController)
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyAppTheme {
        Greeting("a")
    }
}