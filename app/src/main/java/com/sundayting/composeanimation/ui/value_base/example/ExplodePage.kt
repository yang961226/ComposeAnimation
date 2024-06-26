package com.sundayting.composeanimation.ui.value_base.example

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

object ExplodePage {

    const val ROUTE = "explode_page"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController = rememberNavController(),
    ) {
        Scaffold(modifier, topBar = {
            TopAppBar(
                title = { Text("爆炸场景") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        }) { paddingValues ->

            val scope = rememberCoroutineScope()
            var sizeTag by remember { mutableStateOf(false) }
            val animateScale = remember {
                Animatable(1f)
            }
            LaunchedEffect(Unit) {
                snapshotFlow { sizeTag }.drop(1).collect {
                    scope.launch {
                        animateScale.animateTo(
                            1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioHighBouncy,
                            ),
                            initialVelocity = 20f
                        )
                    }
                }
            }

            Box(Modifier.padding(paddingValues), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.padding(20.dp)) {
                        Text(
                            "💣",
                            style = TextStyle(fontSize = 100.sp),
                            modifier = Modifier.graphicsLayer {
                                scaleX = animateScale.value
                                scaleY = animateScale.value
                                rotationZ = animateScale.value * 360
                            })

                        AnimatedVisibility(
                            visible = animateScale.isRunning,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Text("💥", style = TextStyle(fontSize = 100.sp))
                        }
                    }


                    Spacer(Modifier.height(50.dp))

                    Button(onClick = { sizeTag = !sizeTag }) {
                        Text("点我试试")
                    }

                    Card(Modifier.padding(20.dp)) {
                        Text(
                            """
                                实现这个需求不能使用animate*AsState()，因为这个api会监听targetValue的变化，而当前需求是由「当前值」到「当前值」。
                                
                                因此只能退而其次，使用低阶api：「Animatable」，这个api提供了初始速度的参数，基于这个初始速度，开发者可以在不改变目标值的情况下，启动一个动画，动画最终会回归初始值。
                            """.trimIndent(),
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    }

}