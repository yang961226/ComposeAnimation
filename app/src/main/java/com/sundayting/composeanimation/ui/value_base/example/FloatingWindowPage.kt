package com.sundayting.composeanimation.ui.value_base.example

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sundayting.composeanimation.R

object FloatingWindowPage {

    const val ROUTE = "floating_window_page"


    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController = rememberNavController(),
    ) {
        Scaffold(modifier, topBar = {
            TopAppBar(
                title = { Text("浮窗场景") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        }) { paddingValues ->
            BoxWithConstraints(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {

                var isSpring by remember {
                    mutableStateOf(true)
                }

                Column(
                    Modifier
                        .align(Alignment.Center)
                        .padding(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card {
                        Text(
                            """
                    场景：浮窗悬浮在屏幕周围，通过拖动（或长按后拖动）来控制悬浮窗的位移，松手后根据实际场景回归到屏幕边缘
                    
                    要点：使用animateIntOffsetAsState()来控制浮窗的位移，其默认动画规范为spring，给浮窗的位移提供了少许「滞后」感，提高了浮窗的物理感。
                """.trimIndent(), modifier = Modifier

                                .padding(10.dp)
                        )
                    }
                    Spacer(Modifier.height(20.dp))

                    Button(onClick = { isSpring = !isSpring }) {
                        Text("当前动画规范：${if (isSpring) "spring" else "线性"}")
                    }
                }

                val density = LocalDensity.current

                val windowWidthPx = with(density) {
                    150.dp.roundToPx()
                }

                val maxWidthPx = with(density) { maxWidth.roundToPx() }
                val maxHeightPx = with(density) { maxHeight.roundToPx() }

                var offset by remember(density) {
                    mutableStateOf(IntOffset(0, with(density) { ((-100).dp).roundToPx() }))
                }

                val floatingWindowCenterX by rememberUpdatedState(
                    newValue = offset.x - windowWidthPx / 2 + with(
                        density
                    ) { maxWidth.roundToPx() })

                //移动到屏幕侧边
                fun onDragEndOrStop() {
                    offset = if (floatingWindowCenterX > (maxWidthPx / 2)) {
                        //当前浮窗在屏幕右边
                        offset.copy(
                            x = 0,
                            offset.y.coerceIn(
                                minimumValue = -maxHeightPx + with(density) { 100.dp.roundToPx() },
                                maximumValue = 0
                            )
                        )
                    } else {
                        //当前浮窗在屏幕左边
                        offset.copy(
                            x = with(density) {
                                -maxWidth.roundToPx() + windowWidthPx
                            },
                            offset.y.coerceIn(
                                minimumValue = -maxHeightPx + with(density) { 100.dp.roundToPx() },
                                maximumValue = 0
                            )
                        )
                    }
                }

                val animatedOffset by animateIntOffsetAsState(
                    targetValue = offset,
                    label = "",
                    animationSpec = if (isSpring) spring(
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ) else snap()
                )
                var isDragging by remember {
                    mutableStateOf(false)
                }

                FloatingWindow(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .offset { animatedOffset }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDrag = { _, dragAmount ->
                                    offset += dragAmount.round()
                                },
                                onDragCancel = {
                                    isDragging = false
                                    onDragEndOrStop()
                                },
                                onDragEnd = {
                                    isDragging = false
                                    onDragEndOrStop()
                                },
                                onDragStart = {
                                    isDragging = true
                                }
                            )
                        },
                    isDragging = isDragging
                )
            }
        }
    }

    @Composable
    private fun FloatingWindow(
        modifier: Modifier = Modifier,
        isDragging: Boolean,
    ) {
        Box(
            modifier = modifier
                .size(150.dp, 100.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(3.dp, Color.White.copy(0.7f), RoundedCornerShape(10.dp))
        ) {
            Crossfade(targetState = isDragging, label = "") { isDraggingTarget ->
                Image(
                    painterResource(id = if (isDraggingTarget) R.drawable.floating_window_1 else R.drawable.floating_window_2),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewScreen() {
    FloatingWindowPage.Screen(
        Modifier
            .fillMaxSize()
            .background(Color.DarkGray.copy(0.2f))
    )
}

