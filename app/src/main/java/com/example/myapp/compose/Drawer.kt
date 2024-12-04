package com.example.myapp.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.model.LoginViewModel
import com.example.myapp.model.activityViewModel

@Composable
fun DrawerContent(loginViewModel: LoginViewModel = activityViewModel()) {
    val username by loginViewModel.username.observeAsState("Not Login")
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.7f),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
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
                modifier = Modifier.clickable { }
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
                modifier = Modifier.clickable { },
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("加入家庭") },
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
                headlineContent = { Text("创建家庭") },
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
                headlineContent = { Text("创建区域") },
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
                headlineContent = { Text("创建场景") },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable { },
            )
        }
    }
}