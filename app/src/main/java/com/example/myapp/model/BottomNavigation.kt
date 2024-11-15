package com.example.myapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myapp.R
import com.example.myapp.model.NavigationModel.bottomNavList
import com.example.myapp.pages.HomeView
import com.example.myapp.pages.MyView
import com.example.myapp.pages.OverView
import com.example.myapp.pages.SceneView

data class BottomNavigation(
    val label: String,
    val navigate: String,
    val icon: @Composable () -> Unit,
    val composable: @Composable () -> Unit = {}
)


@Composable
fun InitModel() {
    val labels = listOf(
        stringResource(R.string.bottom_nav_overview_label),
        stringResource(R.string.bottom_nav_house_label),
        stringResource(R.string.bottom_nav_scene_label),
        stringResource(R.string.bottom_nav_my_label)
    )
    val inner = listOf(
        stringResource(R.string.bottom_nav_overview_navigation),
        stringResource(R.string.bottom_nav_house_navigation),
        stringResource(R.string.bottom_nav_scene_navigation),
        stringResource(R.string.bottom_nav_my_navigation)
    )
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Favorite,
        Icons.Filled.Build,
        Icons.Filled.AccountCircle
    )

    bottomNavList = labels.indices.map { i ->
        BottomNavigation(
            label = labels[i],
            icon = {
                Icon(
                    icons[i],
                    contentDescription = labels[i]
                )
            },
            navigate = inner[i],
            composable = {
                when (i) {
                    0 -> OverView()
                    1 -> HomeView()
                    2 -> SceneView()
                    3 -> MyView()
                }
            }
        )
    }
}

object NavigationModel {
    lateinit var bottomNavList: List<BottomNavigation>
}
