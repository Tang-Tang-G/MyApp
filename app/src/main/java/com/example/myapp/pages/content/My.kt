package com.example.myapp.pages.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.model.LoginViewModel
import com.example.myapp.model.activityViewModel
import com.example.myapp.screens.my.ChangePassword
import com.example.myapp.screens.my.MyStartView
import com.example.myapp.screens.my.UserInfoPage

@Composable
fun MyView(
    loginViewModel: LoginViewModel = activityViewModel(),
    onLogout: () -> Unit = {}
) {
    val username by loginViewModel.username.observeAsState("Not Login")
    val loginNavController = rememberNavController()
    val changePasswordNav = stringResource(R.string.change_password_navigate)
    val userInfoNav = stringResource(R.string.user_info_navigate)
    //nav
    val myStartView = "MyStartView"
    NavHost(
        navController = loginNavController,
        startDestination = myStartView
    ) {
        composable(myStartView) {
            MyStartView(
                username = username,
                onLogout = onLogout,
                onUserInfoClick = { loginNavController.navigate(userInfoNav) },
                onChangePasswordClick = { loginNavController.navigate(changePasswordNav) }
            )
        }
        composable(changePasswordNav) {
            ChangePassword(loginNavController)
        }
        composable(userInfoNav) {
            UserInfoPage(loginNavController)
        }
    }
}

@Preview
@Composable
fun MyViewPreview() {
    MyView()
}