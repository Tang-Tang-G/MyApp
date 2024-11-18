package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.model.InitModel
import com.example.myapp.model.LoginViewModel
import com.example.myapp.screens.Content
import com.example.myapp.screens.login.ForgetPassword
import com.example.myapp.screens.login.Login
import com.example.myapp.screens.login.SignUp
import com.example.myapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    // Don't pass it to compose
    private val viewModel: LoginViewModel by viewModels()

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

@Preview(showBackground = true)
@Composable
fun MainView() {
    val navController = rememberNavController()
    val loginNav = stringResource(R.string.screen_nav_login_navigation)
    val contentNav = stringResource(R.string.screen_nav_content_navigation)
    val signupNav = stringResource(R.string.screen_nav_signup_navigation)
    val forgetPasswordNav = stringResource(R.string.screen_nav_forget_password_navigation)
    var logined by rememberSaveable { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = if (!logined) loginNav else contentNav,
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
                    // This is avoid back from content to login
                    navController.navigate(contentNav) {
                        popUpTo(loginNav) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    logined = true
                },
                navigateToSignup = {
                    // TODO: judge whether to popBackStack
                    navController.navigate(signupNav)
                },
                navigateToForgetPassword = {
                    // TODO: judge whether to popBackStack
                    navController.navigate(forgetPasswordNav)
                }
            )
        }
        composable(contentNav) {
            Content(navController)
        }
        composable(signupNav) {
            // TODO: Reduce responsibilities
            SignUp(navController)
        }
        composable(forgetPasswordNav) {
            // TODO: Reduce responsibilities
            ForgetPassword(navController)
        }
    }
}