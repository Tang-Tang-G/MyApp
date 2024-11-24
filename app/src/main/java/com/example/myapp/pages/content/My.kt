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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.model.LoginViewModel

@Composable
fun MyView(loginViewModel: LoginViewModel = activityViewModel(), onLogout: () -> Unit = {}) {
    val username by loginViewModel.username.observeAsState("Not Login")
    val loginNavController = rememberNavController()
    //nav
    val changePasswordNav = stringResource(R.string.change_password_navigate)
    val userInfoNav = stringResource(R.string.user_info_navigate)
    val myStartView = "MyStartView"
        NavHost(
            navController = loginNavController,
            startDestination = myStartView
        ) {
            composable(myStartView) {
                MyStartView(username = username,
                    onLogout = onLogout,
                    onUserInfoClick = {loginNavController.navigate(userInfoNav)},
                    onChangePasswordClick = {loginNavController.navigate(changePasswordNav)}
                )
            }
            composable(changePasswordNav) {
                ChangePassword(loginNavController)
            }
            composable(userInfoNav) {
                UserInfo(loginNavController)
            }
        }
}

@Preview
@Composable
fun MyViewPreview() {
    MyView()
}