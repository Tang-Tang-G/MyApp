package com.example.myapp.screens.my

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapp.compose.TopBarWithBack
import com.example.myapp.model.LoginViewModel
import com.example.myapp.model.activityViewModel
import com.example.myapp.network.AccountManager
import com.example.myapp.network.updateAccount
import kotlinx.coroutines.launch

@Composable
fun ChangeAccountInfo(navController: NavController) {
    // 状态保存文本框中的输入值
    val oldPassword = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    var username: String? by remember { mutableStateOf(null) }
    val isOldPasswordValid = remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = activityViewModel()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopBarWithBack(
                title = "修改账户信息",
                goBack = { navController.popBackStack() }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            TextField(
                value = oldPassword.value,
                onValueChange = {
                    oldPassword.value = it
                    isOldPasswordValid.value = it.isNotEmpty()
                },
                label = { Text("原密码（必填）") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                isError = !isOldPasswordValid.value
            )
            if (!isOldPasswordValid.value) {
                Text("原密码为必填项", color = androidx.compose.ui.graphics.Color.Red)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            TextField(
                value = newPassword.value,
                onValueChange = { newPassword.value = it },
                label = { Text("新密码") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            TextField(
                value = username ?: "",
                onValueChange = { username = it },
                label = { Text("新用户名") },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            Button(onClick = {
                if (oldPassword.value.isEmpty()) {
                    isOldPasswordValid.value = false
                } else {
                    scope.launch {
                        val ok = AccountManager.updateAccount(
                            oldPassword = oldPassword.value,
                            newPassword = if (newPassword.value.isEmpty()) null else newPassword.value,
                            username = if (username.isNullOrEmpty()) null else username
                        )
                        if (ok) {
                            username?.let {
                                loginViewModel.updateUserName(it)
                            }
                            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT)
                                .show()
                            navController.popBackStack()
                        }
                    }

                }
            }) {
                Text(text = "提交")
            }
        }
    }
}
