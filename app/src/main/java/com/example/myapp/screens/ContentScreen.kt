package com.example.myapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.compose.BottomBar
import com.example.myapp.compose.ContentFloatButton
import com.example.myapp.compose.ContentTopBar
import com.example.myapp.compose.DrawerContent
import com.example.myapp.model.NavigationModel
import com.example.myapp.pages.content.HomeView
import com.example.myapp.pages.content.MyView
import com.example.myapp.pages.content.OverView
import com.example.myapp.pages.content.SceneView
import kotlinx.coroutines.launch

@Composable
fun Content(screenNavController: NavController) {
    val bottomNavList = NavigationModel.bottomNavList

    // The current content page
    val pageSelection = remember { mutableIntStateOf(0) }
    // used for navigate different content page
    val bottomNavController = rememberNavController()

    val overviewNav = stringResource(R.string.bottom_nav_overview_navigation)
    val houseNav = stringResource(R.string.bottom_nav_house_navigation)
    val sceneNav = stringResource(R.string.bottom_nav_scene_navigation)
    val myNav = stringResource(R.string.bottom_nav_my_navigation)
    val loginNav = stringResource(R.string.screen_nav_login_navigation)
    val contentNav = stringResource(R.string.screen_nav_content_navigation)

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val switchDrawer = {
        scope.launch {
            drawerState.apply {
                if (isOpen) close() else open()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent() }
    ) {
        Scaffold(
            topBar = {
                ContentTopBar(
                    title = {
                        Text(
                            text = bottomNavList[pageSelection.intValue].label,
                        )
                    },
                    onMenuCLicked = { switchDrawer() }
                )
            },
            bottomBar = {
                BottomBar(
                    navController = bottomNavController,
                    navigation = bottomNavList,
                    selectIndex = pageSelection
                )
            },
            floatingActionButton = {
                ContentFloatButton(onClick = {}, show = pageSelection.intValue == 0)
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = bottomNavController,
                startDestination = stringResource(R.string.bottom_nav_overview_navigation),
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(overviewNav)
                {
                    OverView()
                }
                composable(houseNav)
                {
                    HomeView()
                }
                composable(sceneNav)
                {
                    SceneView()
                }
                composable(myNav) {
                    MyView(onLogout = {
                        screenNavController.navigate(loginNav) {
                            popUpTo(contentNav) {
                                inclusive = true
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
                }

            }
        }
    }
}

@Preview
@Composable
fun ContentPreview() {
    Content(rememberNavController())
}