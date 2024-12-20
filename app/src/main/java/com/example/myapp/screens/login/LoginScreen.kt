package com.example.myapp.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.compose.TopBar
import com.example.myapp.model.LoginViewModel
import com.example.myapp.model.SessionManager
import com.example.myapp.model.activityViewModel
import com.example.myapp.network.AccountManager
import com.example.myapp.network.login
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Login(
    navigateToContent: () -> Unit = {},
    navigateToSignup: () -> Unit = {},
    navigateToForgetPassword: () -> Unit = {},
    loginViewModel: LoginViewModel = activityViewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    LocalSoftwareKeyboardController.current

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
                onValueChange = { value -> username = value },
                label = { Text(stringResource(R.string.login_screen_username_text_field_placeholder)) },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(50.dp))
            TextField(
                value = password,
                onValueChange = { value -> password = value },
                label = { Text(stringResource(R.string.login_screen_password_text_field_placeholder)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                )
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    // Finish input then hide the keyboard, 2 chose 1
                    focusManager.clearFocus()
//                    keyboardController?.hide()

                    if (checked) {
                        scope.launch {
                            // username and password get from the remember value
                            val jwt = AccountManager.login(username, password)
                            if (jwt != null) {
                                loginViewModel.setLoginInfo(username, jwt.token)
                                SessionManager.saveSession(
                                    context,
                                    username,
                                    jwt.token
                                )
                                val job = launch {
                                    snackbarHostState.showSnackbar(
                                        "登入成功",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                delay(500)
                                job.cancel()
                                delay(500)
                                navigateToContent()
                            } else {
                                snackbarHostState.showSnackbar("登入出错")
                            }
                        }
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