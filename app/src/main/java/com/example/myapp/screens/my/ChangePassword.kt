package com.example.myapp.screens.my

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myapp.compose.TopBarWithBack

@Composable
fun ChangePassword(navController: NavController) {
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
                title = "修改密码",
                goBack = { navController.popBackStack() }
            )
        }
    }


}