package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.compose.AddHouseDialog
import com.example.myapp.compose.CreateAreaDialog
import com.example.myapp.compose.CreateHouseDialog
import com.example.myapp.compose.CreateSceneDialog
import com.example.myapp.model.InitModel
import com.example.myapp.model.LoginViewModel
import com.example.myapp.model.SessionManager
import com.example.myapp.model.activityViewModel
import com.example.myapp.network.AccountManager
import com.example.myapp.network.auth
import com.example.myapp.screens.Content
import com.example.myapp.screens.login.ForgetPassword
import com.example.myapp.screens.login.Login
import com.example.myapp.screens.login.SignUp
import com.example.myapp.screens.my.ChangeAccountInfo
import com.example.myapp.screens.my.UserInfoPage
import com.example.myapp.screens.pair.PairDevice
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                InitModel()
                MainView()
            }
        }
    }
}


fun NavController.navigateTo(route: String) {
    this.navigate(route) {
        popBackStack()
    }
}


@Composable
fun MainView() {
    val navController = rememberNavController()
    val loginNav = stringResource(R.string.screen_nav_login_navigation)
    val contentNav = stringResource(R.string.screen_nav_content_navigation)
    val signupNav = stringResource(R.string.screen_nav_signup_navigation)
    val forgetPasswordNav = stringResource(R.string.screen_nav_forget_password_navigation)


    val context = LocalContext.current
    SessionManager.getToken(context)?.let { token ->
        val loginViewModel: LoginViewModel = activityViewModel()
        val scope = rememberCoroutineScope()
        scope.launch {
            val ok = AccountManager.auth(token)
            if (!ok) {
                navController.navigateTo(loginNav)
                Log.d("Auth", "need login")
            } else {
                loginViewModel.initializeFromSession(context)
                // TODO: show that the login session is expired, need re-login
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
        enterTransition = {
            slideInHorizontally { fullWidth -> fullWidth }
        },
        exitTransition = {
            slideOutHorizontally { fullWidth -> -fullWidth } // 页面向左侧退出
        },
        popEnterTransition = {
            slideInHorizontally { fullWidth -> -fullWidth } // 返回时从左侧进入
        },
        popExitTransition = {
            slideOutHorizontally { fullWidth -> fullWidth } // 返回时向右侧退出
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
                },
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
        composable("UserInfo"){
            UserInfoPage(navController)
        }
        composable("ChangePassword"){
            ChangeAccountInfo(navController)
        }
        composable("AddHouse"){
            AddHouseDialog(goBack = {
                navController.popBackStack()
            })
        }
        composable("CreateHouse"){
            CreateHouseDialog(goBack = {
                navController.popBackStack()
            })
        }
        composable("CreateScene"){
            CreateSceneDialog(goBack = {
                navController.popBackStack()
            })
        }
        composable("CreateArea"){
            CreateAreaDialog(goBack = {
                navController.popBackStack()
            })
        }
    }
}