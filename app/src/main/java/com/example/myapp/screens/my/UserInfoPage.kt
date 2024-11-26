package com.example.myapp.screens.my

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapp.compose.TopBarWithBack
import com.example.myapp.model.MutableUserInfo
import com.example.myapp.model.SessionManager
import com.example.myapp.network.AccountManager
import com.example.myapp.model.UserInfo
import com.example.myapp.network.fetchUserInfo
import kotlinx.coroutines.launch

@Composable
fun UserInfoPage(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userInfoState = remember { mutableStateOf<UserInfo?>(null) }
    val isEditing = remember { mutableStateOf(false) }
    val mutableUserInfo = remember { mutableStateOf(MutableUserInfo("", "", 0, "", "")) }

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
                        val userinfo = AccountManager.fetchUserInfo(token)
                        userInfoState.value = userinfo
                        if (userinfo != null) {
                            mutableUserInfo.value = MutableUserInfo(
                                userinfo.name,
                                userinfo.gender,
                                userinfo.age,
                                userinfo.city,
                                userinfo.email
                            )
                        }
                    }
                }
            }

            if (!isEditing.value) {
                userInfoState.value?.let { info ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row {
                            Text(text = "姓名: ")
                            Text(text = info.name)
                        }
                        Row {
                            Text(text = "性别: ")
                            Text(text = info.gender)
                        }
                        Row {
                            Text(text = "年龄: ")
                            Text(text = info.age.toString())
                        }
                        Row {
                            Text(text = "城市: ")
                            Text(text = info.city)
                        }
                        Row {
                            Text(text = "邮箱: ")
                            Text(text = info.email)
                        }
                    }
                } ?: run {
                    Text(text = "Loading...")
                }
                Button(onClick = { isEditing.value = true }) {
                    Text(text = "编辑")
                }
            } else {
                EditUserInfoForm(mutableUserInfo = mutableUserInfo,
                    onDone = { updatedUserInfo ->
                        userInfoState.value = UserInfo(
                            name = updatedUserInfo.name,
                            gender = updatedUserInfo.gender,
                            age = updatedUserInfo.age,
                            city = updatedUserInfo.city,
                            email = updatedUserInfo.email
                        )
                        isEditing.value = false
                    })
            }
        }
    }
}

@Composable
fun EditUserInfoForm(
    mutableUserInfo: MutableState<MutableUserInfo>,
    onDone: (MutableUserInfo) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TextField(value = mutableUserInfo.value.age.toString(),
            onValueChange = { mutableUserInfo.value = mutableUserInfo.value.copy(age = it.toInt()) })
        TextField(
            value = mutableUserInfo.value.gender,
            onValueChange = { mutableUserInfo.value = mutableUserInfo.value.copy(gender = it) })
        TextField(
            value = mutableUserInfo.value.city,
            onValueChange = { mutableUserInfo.value = mutableUserInfo.value.copy(city = it) })
        TextField(
            value = mutableUserInfo.value.email,
            onValueChange = { mutableUserInfo.value = mutableUserInfo.value.copy(email = it) })
        Row(
            modifier = Modifier.fillMaxWidth(), // 让 Row 填满父组件的宽度
            horizontalArrangement = spacedBy(16.dp)
        ) {
            Button(onClick = { onDone(mutableUserInfo.value) }) {
                Text(text = "保存")
            }
            Button(onClick = { onDone(mutableUserInfo.value) }) {
                Text(text = "取消")
            }
        }
    }
}