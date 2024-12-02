package com.example.myapp.screens.my

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import com.example.myapp.model.UserInfo
import com.example.myapp.network.AccountManager
import com.example.myapp.network.fetchUserInfo
import com.example.myapp.network.updateUserInfo
import kotlinx.coroutines.launch

@Composable
fun UserInfoPage(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userInfoState = remember { mutableStateOf<UserInfo?>(null) }
    val isEditing = remember { mutableStateOf(false) }
    val mutableUserInfo = remember { mutableStateOf(UserInfo(null, null, null, null, null)) }

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
                        val userinfo = AccountManager.fetchUserInfo()
                        userInfoState.value = userinfo
                        if (userinfo != null) {
                            mutableUserInfo.value = UserInfo(
                                name = userinfo.name,
                                email = userinfo.email,
                                age = userinfo.age,
                                gender = userinfo.gender,
                                city = userinfo.city,
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
                            Text(text = info.name ?: "")
                        }
                        Row {
                            Text(text = "性别: ")
                            Text(text = info.gender ?: "")
                        }
                        Row {
                            Text(text = "年龄: ")
                            Text(text = info.age?.toString() ?: "")
                        }
                        Row {
                            Text(text = "城市: ")
                            Text(text = info.city ?: "")
                        }
                        Row {
                            Text(text = "邮箱: ")
                            Text(text = info.email ?: "")
                        }
                    }
                } ?: run {
                    Text(text = "Loading...")
                }
                Button(onClick = { isEditing.value = true }) {
                    Text(text = "编辑")
                }
            } else {
                EditUserInfoForm(
                    mutableUserInfo = mutableUserInfo,
                    onDone = { updatedUserInfo ->
                        val originalUserInfo = userInfoState.value
                        if ((originalUserInfo != null) &&
                            (originalUserInfo.name != updatedUserInfo.name ||
                                    originalUserInfo.gender != updatedUserInfo.gender ||
                                    originalUserInfo.age != updatedUserInfo.age ||
                                    originalUserInfo.city != updatedUserInfo.city ||
                                    originalUserInfo.email != updatedUserInfo.email)
                        ) {
                            scope.launch {
                                val isUpdated = AccountManager.updateUserInfo(updatedUserInfo)
                                if (isUpdated) {
                                    userInfoState.value = updatedUserInfo // 更新 UI
                                    isEditing.value = false
                                } else {
                                    // 处理更新失败的情况
                                }
                            }
                        } else {
                            // 如果没有变化，不需要提交
                            isEditing.value = false
                        }
                    },
                    onCancel = {
                        isEditing.value = false
                        userInfoState.value?.let {
                            mutableUserInfo.value = UserInfo(
                                name = it.name,
                                email = it.email,
                                age = it.age,
                                gender = it.gender,
                                city = it.city
                            )
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun EditUserInfoForm(
    mutableUserInfo: MutableState<UserInfo>,
    onDone: (UserInfo) -> Unit,
    onCancel: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    TextField(
                        value = mutableUserInfo.value.name ?: "",
                        onValueChange = {
                            mutableUserInfo.value = mutableUserInfo.value.copy(name = it)
                        },
                        label = { Text("姓名") }
                    )
                }
                item {
                    TextField(
                        value = mutableUserInfo.value.gender ?: "",
                        onValueChange = {
                            mutableUserInfo.value = mutableUserInfo.value.copy(gender = it)
                        },
                        label = { Text("性别") }
                    )
                }
                item {
                    TextField(
                        value = mutableUserInfo.value.age.toString(),
                        onValueChange = {
                            val age = it.toIntOrNull()
                            if (age != null) {
                                mutableUserInfo.value = mutableUserInfo.value.copy(age = age)
                            }
                        },
                        label = { Text("年龄") }
                    )
                }
                item {
                    TextField(
                        value = mutableUserInfo.value.city ?: "",
                        onValueChange = {
                            mutableUserInfo.value = mutableUserInfo.value.copy(city = it)
                        },
                        label = { Text("城市") }
                    )
                }
                item {
                    TextField(
                        value = mutableUserInfo.value.email ?: "",
                        onValueChange = {
                            mutableUserInfo.value = mutableUserInfo.value.copy(email = it)
                        },
                        label = { Text("邮箱") }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = spacedBy(60.dp, Alignment.CenterHorizontally)
            ) {
                Button(onClick = { onDone(mutableUserInfo.value) }) {
                    Text(text = "保存")
                }
                Button(onClick = { onCancel() }) {
                    Text(text = "取消")
                }
            }
        }
    }
}
