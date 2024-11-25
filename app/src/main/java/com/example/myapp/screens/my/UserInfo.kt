package com.example.myapp.screens.my

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapp.compose.TopBarWithBack
import com.example.myapp.model.SessionManager
import com.example.myapp.network.AccountManager
import com.example.myapp.model.UserInfo
import com.example.myapp.network.fetchUserInfo
import kotlinx.coroutines.launch

@Composable
fun UserInfo(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userInfo = remember { mutableStateOf<UserInfo?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            TopBarWithBack(
                title = "个人信息",
                goBack = { navController.popBackStack() }
            )

            LaunchedEffect(Unit) {
                scope.launch {
                    val token = SessionManager.getToken(context)
                    if (token != null) {
                        userInfo.value = AccountManager.fetchUserInfo(token)
                    }
                }
            }
            userInfo.value?.let { info ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Text(text = "Name: ")
                        Text(text = info.name)
                    }
                    Row {
                        Text(text = "Age: ")
                        Text(text = info.age.toString())
                    }
                    Row {
                        Text(text = "Email: ")
                        Text(text = info.email)
                    }
                    Row {
                        Text(text = "Gender: ")
                        Text(text = info.gender)
                    }
                }
            } ?: run {
                Text(text = "Loading...")
            }
        }
    }
}
