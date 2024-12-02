package com.example.myapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun DrawerContent() {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.7f),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 20.dp)
        ) {
            Text("Side Bar")
        }
    }
}