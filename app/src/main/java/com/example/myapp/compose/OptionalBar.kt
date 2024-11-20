package com.example.myapp.compose

import com.example.myapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OptionalBar(
    text: String,
    height: Dp = 50.dp,
    backgroundColor: Color = Color.White,
    onclick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .height(height)
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(5.dp)
            .clickable(
                onClick = {
                    onclick()
                }
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(0.8f))
        Icon(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = "Arrow",
        )
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    )
}
