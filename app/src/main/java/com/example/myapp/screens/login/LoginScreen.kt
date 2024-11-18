package com.example.myapp.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.compose.TopBar
import com.example.myapp.ui.theme.MyAppTheme

@Composable
fun Login(
    navigateToContent: () -> Unit = {},
    navigateToSignup: () -> Unit = {},
    navigateToForgetPassword: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
            TopBar(title = { Text(text = "登录") })

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
                value = password,
                onValueChange = { value ->
                    password = value
                },
                label = {
                    Text("password") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = navigateToContent) {
                    Text("Sign in") // TODO: add to strings.xml, replaced by stringResource
                }
                Spacer(modifier = Modifier.width(50.dp))
                Button(onClick = navigateToSignup) {
                    Text("Sign up") // TODO: add to strings.xml, replaced by stringResource
                }
            }
            TextButton(onClick = navigateToForgetPassword) {
                Text("forget password?")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MyAppTheme {
        Login()
    }
}