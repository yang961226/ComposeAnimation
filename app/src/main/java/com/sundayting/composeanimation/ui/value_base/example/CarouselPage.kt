package com.sundayting.composeanimation.ui.value_base.example

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sundayting.composeanimation.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object CarouselPage {

    const val ROUTE = "carousel_page"

    private val bgList = listOf(
        R.drawable.carousel_1,
        R.drawable.carousel_2,
        R.drawable.carousel_3,
        R.drawable.carousel_4,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController = rememberNavController(),
    ) {
        Scaffold(modifier, topBar = {
            TopAppBar(
                title = { Text("展示墙场景") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        }) { paddingValues ->

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(Modifier.padding(20.dp)) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(4f / 3f)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        val distanceOffset = with(LocalDensity.current) { 50.dp.roundToPx() }
                        var curIndex by remember { mutableIntStateOf(0) }

                        AnimatedContent(targetState = curIndex, label = "", transitionSpec = {
                            fadeIn(
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    easing = LinearEasing
                                )
                            ) togetherWith fadeOut(
                                tween(
                                    durationMillis = 1000,
                                    easing = LinearEasing
                                )
                            )
                        }) { targetIndex ->
                            val scrollState = rememberScrollState()
                            LaunchedEffect(Unit) {
                                launch {
                                    scrollState.animateScrollTo(
                                        distanceOffset, animationSpec = tween(
                                            durationMillis = 4000,
                                            easing = LinearEasing
                                        )
                                    )
                                }
                                launch {
                                    delay(3000)
                                    curIndex = (curIndex + 1) % bgList.size
                                }
                            }
                            Image(
                                painter = painterResource(id = bgList[targetIndex]),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .horizontalScroll(state = scrollState, enabled = false),
                                contentScale = ContentScale.FillHeight
                            )
                        }
                    }
                }
                Spacer(Modifier.height(30.dp))

                Card(Modifier.padding(20.dp)) {
                    Text("""
                        使用AnimatedContent()，为不同的图片切换添加过渡，通过结合LaunchedEffect为图片出现的时候启动横移动画
                    """.trimIndent(), modifier = Modifier.padding(10.dp))
                }

            }

        }
    }

}