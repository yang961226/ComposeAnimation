package com.sundayting.composeanimation.ui.value_base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sundayting.composeanimation.ui.value_base.example.CarouselPage
import com.sundayting.composeanimation.ui.value_base.example.ExplodePage
import com.sundayting.composeanimation.ui.value_base.example.FloatingToolPage
import com.sundayting.composeanimation.ui.value_base.example.FloatingWindowPage

object ExamplePage {

    const val ROUTE = "example_page"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController,
    ) {

        Scaffold(modifier, topBar = {
            TopAppBar(
                title = { Text("案例") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        }) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(it),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 50.dp,
                    top = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                item {
                    ExampleItem(
                        onClick = {
                            navHostController.navigate(FloatingWindowPage.ROUTE)
                        }
                    ) {
                        Text("悬浮窗场景：拖动（或长按拖动）悬浮窗，松手后悬浮窗回归到屏幕边缘，带有拟真的「滞后感」")
                    }
                }

                item {
                    ExampleItem(
                        onClick = {
                            navHostController.navigate(FloatingToolPage.ROUTE)
                        }
                    ) {
                        Text("悬浮工具栏场景：拖动工具栏，不同的按钮之间「拖曳感」")
                    }
                }

                item {
                    ExampleItem(
                        onClick = {
                            navHostController.navigate(ExplodePage.ROUTE)
                        }
                    ) {
                        Text("「爆炸」、「弹性」场景：模拟某个东西发生了爆炸或者收到压迫后发生反弹，但最终会回归到初始状态。")
                    }
                }

                item {
                    ExampleItem(
                        onClick = {
                            navHostController.navigate(CarouselPage.ROUTE)
                        }
                    ) {
                        Text("展示墙场景：在不同的图片之间添加顺滑的渐变动画，图片本身有横移效果。")
                    }
                }

            }
        }

    }

    @Composable
    private fun ExampleItem(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        title: @Composable () -> Unit,
    ) {

        Card(
            modifier
                .padding(10.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) { onClick() }
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    title()
                }

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(30.dp)
                )
            }
        }

    }

}