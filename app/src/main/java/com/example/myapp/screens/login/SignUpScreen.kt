package com.example.myapp.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapp.compose.TopBarWithBack
import com.example.myapp.network.AccountManager
import com.example.myapp.network.signup
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUp(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf<String?>(null) }
    var isPasswordError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
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
                title = "注册",
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
                value = password,
                onValueChange = { value ->
                    password = value
                    passwordError = validatePassword(password)
                    isPasswordError = passwordError != null
                },
                label = {
                    Text("password") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = isPasswordError,
                modifier = Modifier.onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        passwordError = validatePassword(password)
                        isPasswordError = passwordError != null
                        passwordError?.let {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
            TextField(
                value = confirmPassword,
                onValueChange = { value ->
                    confirmPassword = value
                },
                label = {
                    Text("Confirm password") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = !password.equals(confirmPassword),
                modifier = Modifier.onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        if (password != confirmPassword) {
                            Toast.makeText(context, "两次输入的密码不一致", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
            TextField(
                value = email,
                onValueChange = { value ->
                    email = value
                },
                label = {
                    Text("Email") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    scope.launch {
                        val passwordValidationError = validatePassword(password)
                        val confirmPasswordValidationError = if (password != confirmPassword) {
                            "两次输入的密码不一致"
                        } else null
                        if (passwordValidationError != null || confirmPasswordValidationError != null) {
                            passwordError = passwordValidationError
                            confirmPasswordValidationError?.let {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val loginInfo =
                                AccountManager.signup(username = username, password = password)
                            if (loginInfo == null) {
                                val job = launch {
                                    snackBarHostState.showSnackbar(
                                        "注册成功",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                delay(500)
                                job.cancel()
                                navController.popBackStack()
                            } else {
                                snackBarHostState.showSnackbar("注册失败,用户名已经存在")
                            }
                        }
                    }

                }
            ) {
                Text("Sign up") // TODO: add to strings.xml, replaced by stringResource
            }
        }
    }
}

fun validatePassword(password: String): String? {
    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
    return if (!password.matches(passwordRegex.toRegex())) {
        "密码必须至少8位，并且包含字母和数字"
    } else null
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    SignUp(rememberNavController())
}