package com.example.myapp.screens.my

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.compose.OptionalBar
import com.example.myapp.model.LoginViewModel

@Composable
fun MyStartView(
    username: String, onLogout: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onUserInfoClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(
                    modifier = Modifier.fillMaxWidth(0.05f)
                )
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "self",
                    modifier = Modifier.fillMaxSize(0.15f)
                )
                Text(
                    text = username,
                    fontSize = 24.sp,
                )
            }

            OptionalBar(
                text = "个人信息",
                onclick = onUserInfoClick
            )
            OptionalBar(
                text = "修改密码",
                onclick = onChangePasswordClick
            )
            OptionalBar(
                text = "联系客服",
                onclick = {
                }
            )
            OptionalBar(
                text = "隐私政策",
            )
            OptionalBar(
                text = "关于App",
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            Button(
                onClick = {
                    //点击以后退出登录
                    LoginViewModel.logout();
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth(0.5f),
            ) {
                Text("退出登录")
            }
        }
    }
}

