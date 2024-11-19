package com.example.myapp.pages.content

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.compose.OptionalBar
import com.example.myapp.model.LoginViewModel

@Composable
fun MyView() {
    val loginViewModel = LoginViewModel
    val username by loginViewModel.username.observeAsState("Not login")
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "self",
                    modifier = Modifier.fillMaxSize(0.15f)
                )

                    Text(
                        text = username,
                        fontSize = 24.sp,
                        modifier = Modifier.clickable(
                            onClick = {
                                if(username == "Not login")
                                {
                                    //TODO 点击以后，返回到登录界面
                                    Toast.makeText(context,"打开登录界面", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    )
            }
            OptionalBar("个人信息",
                onClick = {
                    //TODO 跳转到个人信息页面

                })
            OptionalBar("App信息",
                onClick = {
                    //TODO 跳转到个人信息页面

                })

            Spacer(modifier = Modifier.fillMaxWidth().height(50.dp))
            Button(
                onClick = {
                    //点击以后退出登录
                    LoginViewModel.logout();
                    //TODO 实现界面退出导航
            },
            modifier = Modifier.fillMaxWidth(0.5f),
                ) {
            Text("退出登录")
            }
        }
    }
}

@Preview
@Composable
fun MyViewPreview() {
    MyView()
}