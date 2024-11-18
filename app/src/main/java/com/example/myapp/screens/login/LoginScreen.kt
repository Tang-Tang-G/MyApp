package com.example.myapp.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.compose.TopBar
import com.example.myapp.network.login
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

@Composable
fun Login(
    navigateToContent: () -> Unit = {},
    navigateToSignup: () -> Unit = {},
    navigateToForgetPassword: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
//
//    val loginViewModel = LoginViewModel()
//    val loginResult = loginViewModel.loginResult.observe()

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
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Account",
                modifier = Modifier.fillMaxSize(0.2f)
            )
            Spacer(modifier = Modifier.height(50.dp))
            TextField(
                value = username,
                onValueChange = { value ->
                    username = value
                },
                label = {
                    Text("用户名") // TODO: add to strings.xml, replaced by stringResource
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
                    Text("输入密码") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    if (checked) {
                        scope.launch {
                            val response = login("wzy", "123456")
                            Log.d(this.javaClass.name, response.orEmpty())
                        }
                        navigateToContent()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("需要确定协议!")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("登录") // TODO: add to strings.xml, replaced by stringResource
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(20.dp)
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = !checked
                    },
                )
                Text(
                    text = "我已阅读并同意服务协议",
                    fontSize = 14.sp
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp)
            ) {
                TextButton(onClick = navigateToSignup) {
                    Text("注册用户") // TODO: add to strings.xml, replaced by stringResource
                }
                TextButton(onClick = navigateToForgetPassword) {
                    Text("忘记密码")
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
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