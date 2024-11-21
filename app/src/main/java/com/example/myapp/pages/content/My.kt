package com.example.myapp.pages.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myapp.model.LoginViewModel
import com.example.myapp.model.activityViewModel


@Composable
fun MyView(loginViewModel: LoginViewModel = activityViewModel()) {
    val username by loginViewModel.username.observeAsState("Not login")
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text("My Page")
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
                Column {
                    Text(
                        text = username,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MyViewPreview() {
    MyView()
}