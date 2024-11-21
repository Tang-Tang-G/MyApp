package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.model.InitModel
import com.example.myapp.model.SessionManager
import com.example.myapp.network.AccountManager
import com.example.myapp.network.auth
import com.example.myapp.screens.Content
import com.example.myapp.screens.login.ForgetPassword
import com.example.myapp.screens.login.Login
import com.example.myapp.screens.login.SignUp
import com.example.myapp.screens.pair.PairDevice
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitModel()
            MyAppTheme {
                MainView()
            }
        }
    }
}


fun NavController.navigateTo(route: String) {
    this.navigate(route) {
        // 清除返回栈，确保只有一个目的地在栈中
        popUpTo(this@navigateTo.graph.findStartDestination().id) {
            saveState = true
        }
        // 防止重复导航
        launchSingleTop = true
        // 恢复状态（如滚动位置等）
        restoreState = true
    }
}


@Preview(showBackground = true)
@Composable
fun MainView() {
    val navController = rememberNavController()
    val loginNav = stringResource(R.string.screen_nav_login_navigation)
    val contentNav = stringResource(R.string.screen_nav_content_navigation)
    val signupNav = stringResource(R.string.screen_nav_signup_navigation)
    val forgetPasswordNav = stringResource(R.string.screen_nav_forget_password_navigation)
    val scope = rememberCoroutineScope()


    SessionManager.getToken(LocalContext.current)?.let { token ->
        scope.launch {
            val ok = AccountManager.auth(token)
            if (!ok) {
                navController.navigateTo(loginNav)
                Log.d("Auth", "need login")
            }
        }
    }


    NavHost(
        navController = navController,
        startDestination = if (SessionManager.getToken(LocalContext.current) != null) {
            contentNav
        } else {
            loginNav
        },
        //TODO: add animation
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }
    ) {
        composable(loginNav) {
            Login(
                navigateToContent = {
                    navController.navigateTo(contentNav)
                },
                navigateToSignup = {
                    navController.navigate(signupNav)
                },
                navigateToForgetPassword = {
                    navController.navigate(forgetPasswordNav)
                }
            )
        }
        composable(contentNav) {
            Content(navController)
        }
        composable(signupNav) {
            SignUp(navController)
        }
        composable(forgetPasswordNav) {
            ForgetPassword(navController)
        }
        composable("add device") {
            PairDevice(goBack = {
                navController.popBackStack()
            })
        }
    }
}