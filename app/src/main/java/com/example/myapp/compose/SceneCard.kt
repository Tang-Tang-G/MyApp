//package com.example.myapp.compose
//
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//
//
//@Composable
//fun SceneCard(
//    sceneInfo: RecommendSceneInfo
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .animateContentSize()
//            .padding(horizontal = 8.dp),
//        elevation = CardDefaults.cardElevation(),
//        shape = RoundedCornerShape(28.dp)
//    ) {
//        Box {
//            Image(
//                painter = painterResource(sceneInfo.imageResourceId),
//                contentDescription = sceneInfo.name,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.FillBounds
//            )
//        }
//        Column(
//            modifier = Modifier
//                .padding(16.dp),
//            horizontalAlignment = Alignment.Start
//        ) {
//            Text(
//                text = sceneInfo.name,
//                color = MaterialTheme.colorScheme.primary,
//                style = MaterialTheme.typography.titleLarge
//            )
//            Text(
//                text = sceneInfo.description,
//                color = MaterialTheme.colorScheme.secondary,
//                style = MaterialTheme.typography.titleMedium
//            )
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .size(100.dp)
//            )
//        }
//    }
//}
