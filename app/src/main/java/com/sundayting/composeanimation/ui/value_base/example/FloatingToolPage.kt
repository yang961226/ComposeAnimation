package com.sundayting.composeanimation.ui.value_base.example

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object FloatingToolPage {

    const val ROUTE = "floating_tool"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController = rememberNavController(),
    ) {

        Scaffold(modifier, topBar = {
            TopAppBar(
                title = { Text("浮动工具栏场景") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        }) { paddingValues ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {

                Card(Modifier.padding(20.dp)) {
                    Text("核心要点：位移动画使用spring()规范，不同的小球使用不同的刚度（刚度越大往目标值移动的速度越快），实现小球的拖曳效果",modifier=Modifier.padding(10.dp))
                }

                Tools()
            }
        }

    }

    @Composable
    fun Tools(
        modifier: Modifier = Modifier,
    ) {

        var offset by remember(Unit) {
            mutableStateOf(IntOffset(0, 0))
        }

        val box1Offset by animateIntOffsetAsState(
            targetValue = offset, label = "", animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessHigh
            )
        )

        val box2Offset by animateIntOffsetAsState(
            targetValue = offset, label = "", animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )

        val box3Offset by animateIntOffsetAsState(
            targetValue = offset, label = "", animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )

        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .offset {
                        box1Offset
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { _, dragAmount ->
                                offset += dragAmount.round()
                            },
                        )
                    }
                    .size(50.dp)
                    .background(Color.White, CircleShape)
                    .background(Color.Red.copy(0.2f), CircleShape)
            )

            Box(
                Modifier
                    .offset {
                        box2Offset
                    }
                    .size(35.dp)
                    .background(Color.White, CircleShape)
                    .background(Color.Blue.copy(0.2f), CircleShape)
            )

            Box(
                Modifier
                    .offset {
                        box3Offset
                    }
                    .size(35.dp)
                    .background(Color.White, CircleShape)
                    .background(Color.Green.copy(0.2f), CircleShape)
            )
        }


    }

}