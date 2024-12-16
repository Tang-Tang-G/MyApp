package com.example.myapp.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapp.compose.TopBarWithBack

@Composable
fun ForgetPassword(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            // 内容从上到下排布
            verticalArrangement = Arrangement.Top,

            // 水平上居中
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            TopBarWithBack(
                title = "忘记密码",
                goBack = { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.height(50.dp))
            TextField(
                value = username,
                onValueChange = { value ->
                    username = value
                },
                label = {
                    Text("username") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(50.dp))
            TextField(
                value = email,
                onValueChange = { value ->
                    email = value
                },
                label = {
                    Text("email") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("查询密码") // TODO: add to strings.xml, replaced by stringResource
            }
        }
    }
}
