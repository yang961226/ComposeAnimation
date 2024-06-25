package com.sundayting.composeanimation.ui.value_base.example

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
                title = { Text("浮窗") },
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

                val density = LocalDensity.current

                val windowWidthPx = with(density) {
                    150.dp.roundToPx()
                }

                val maxWidthPx = with(density) { maxWidth.roundToPx() }


                var offset by remember(density) {
                    mutableStateOf(IntOffset(0, with(density) { ((-100).dp).roundToPx() }))
                }

                val floatingWindowCenterX by rememberUpdatedState(newValue = offset.x - windowWidthPx / 2 + with(density) { maxWidth.roundToPx() })

                Column(
                    Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("maxWidth:${maxWidthPx}")
                    Text("maxWidth一半:${maxWidthPx / 2}")
                    Text("floatingWindowCenterX:${floatingWindowCenterX}")
                }

                fun moveToInitDest() {
                    offset = if (floatingWindowCenterX > (maxWidthPx / 2)) {
                        //当前浮窗在屏幕右边
                        offset.copy(x = 0)
                    } else {
                        //当前浮窗在屏幕左边
                        offset.copy(x = with(density) {
                            -maxWidth.roundToPx() + windowWidthPx
                        })
                    }
                }

                val animatedOffset by animateIntOffsetAsState(
                    targetValue = offset,
                    label = "",
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
                                    moveToInitDest()
                                },
                                onDragEnd = {
                                    isDragging = false
                                    moveToInitDest()
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

