package com.example.myapp.screens.my

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.model.SessionManager

@Composable
fun MyStartView(
    username: String,
    onLogout: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onUserInfoClick: () -> Unit = {},
) {
    val context = LocalContext.current

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

            ListItem(
                headlineContent = { Text("个人信息") },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable { onUserInfoClick() }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("修改密码") },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable { onChangePasswordClick() },
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("联系客服") },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable { },
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("隐私政策") },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable { },
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("关于App") },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable { },
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            Button(
                onClick = {
                    //点击以后退出登录
                    SessionManager.clearSession(context)
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth(0.5f),
            ) {
                Text("退出登录")
            }
        }
    }
}

