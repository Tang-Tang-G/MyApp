package com.example.myapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
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
            TextField(
                value = username,
                onValueChange = { value ->
                    username = value
                },
                label = {
                    Text("username") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true
            )
            TextField(
                value = password,
                onValueChange = { value ->
                    password = value
                },
                label = {
                    Text("password") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = navigateToContent) {
                    Text("login") // TODO: add to strings.xml, replaced by stringResource
                }
                Button(onClick = navigateToSignup) {
                    Text("signup") // TODO: add to strings.xml, replaced by stringResource
                }
            }
            Text(
                text = "forget password?",
                modifier = Modifier
                    .clickable {
                        navigateToForgetPassword()
                    },
                color = Color.Gray,
                textDecoration = TextDecoration.Underline
            )
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