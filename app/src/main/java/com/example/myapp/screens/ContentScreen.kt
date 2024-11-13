package com.example.myapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.compose.BottomBar
import com.example.myapp.compose.DrawerContent
import com.example.myapp.compose.TopBar
import com.example.myapp.pages.HomeView
import com.example.myapp.pages.MyView
import com.example.myapp.pages.OverView
import com.example.myapp.pages.SceneView

@Preview(showBackground = true)
@Composable
fun Content(navController: NavController = rememberNavController()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // TODO: unit
    val pageSelection by remember { mutableIntStateOf(0) }

    val bottomNavController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent() }
    ) {
        Scaffold(
            topBar = {
                TopBar {

                }
            },
            bottomBar = { BottomBar(bottomNavController) },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = bottomNavController,
                startDestination = "overview",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("overview") {
                    OverView()
                }
                composable("home") {
                    HomeView()
                }
                composable("scene") {
                    SceneView()
                }
                composable("my") {
                    MyView()
                }
            }
        }
    }
}