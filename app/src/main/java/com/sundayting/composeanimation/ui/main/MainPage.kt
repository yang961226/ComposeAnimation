package com.sundayting.composeanimation.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sundayting.composeanimation.R

object MainPage {

    const val ROUTE = "main_page"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController,
    ) {

        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painterResource(id = R.drawable.jetpack_compose_icon),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(Modifier.width(10.dp))
                            Text("Jetpack Compose动画小课堂")
                        }
                    },
                )
            }
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentPadding = PaddingValues(start = 15.dp, end = 15.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item(key = "这是什么") {
                    ElevatedCard {
                        var expandMore by remember {
                            mutableStateOf(false)
                        }
                        Box(Modifier.fillMaxWidth()) {
                            val buttonRotateAngle by animateFloatAsState(
                                targetValue = if (expandMore) 180f else 0f,
                                label = ""
                            )
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(4f / 2f)
                                    .clip(CardDefaults.elevatedShape)
                                    .shadow(elevation = 5.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.main_page_2),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    Modifier
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth()
                                        .height(80.dp)
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.Black)
                                            )
                                        )
                                )
                            }

                            val titleTransition = rememberInfiniteTransition(label = "")
                            val titleOffset by titleTransition.animateValue(
                                initialValue = 0.dp,
                                targetValue = 25.dp,
                                typeConverter = Dp.VectorConverter,
                                animationSpec = remember {
                                    InfiniteRepeatableSpec(
                                        animation = tween(durationMillis = 2_000),
                                        repeatMode = RepeatMode.Reverse
                                    )
                                }, label = ""
                            )
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilledIconButton(
                                    onClick = { expandMore = !expandMore },
                                    modifier = Modifier.graphicsLayer {
                                        rotationZ = buttonRotateAngle
                                    }
                                ) {
                                    Icon(Icons.Sharp.KeyboardArrowDown, contentDescription = null)
                                }
                                Text(
                                    text = "👈🏻这个应用的目的是什么？",
                                    modifier = Modifier
                                        .offset {
                                            IntOffset(titleOffset.roundToPx(), 0)
                                        },
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colorScheme.inverseOnSurface
                                    )
                                )
                                Spacer(
                                    Modifier
                                        .fillMaxWidth()
                                        .weight(1f, false)
                                )
                            }
                        }
                        AnimatedVisibility(visible = expandMore) {
                            CompositionLocalProvider(
                                LocalTextStyle provides MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Column(Modifier.padding(10.dp)) {
                                    Text(
                                        modifier = Modifier.padding(vertical = 15.dp),
                                        text = "结合理论+代码+实际场景的方式让读者入门并精通Compose动画。",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text("📚 只需要简单的理论基础。")
                                    Text("💻 每个案例均有对应的代码，可以动手微调练习。")
                                    Text("🌄 只会知识却不会如何在项目中使用？这里为常见场景提供了模版代码！")
                                }
                            }

                        }

                    }
                }
                item(key = "为什么要使用动画") {
                    ElevatedCard {
                        var expandMore by remember {
                            mutableStateOf(false)
                        }
                        Box(Modifier.fillMaxWidth()) {

                            val buttonRotateAngle by animateFloatAsState(
                                targetValue = if (expandMore) 180f else 0f,
                                label = ""
                            )
                            Image(
                                painter = painterResource(id = R.drawable.main_page_1),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(4f / 2f)
                                    .clip(CardDefaults.elevatedShape)
                                    .shadow(elevation = 5.dp),
                                contentScale = ContentScale.Crop
                            )
                            val titleTransition = rememberInfiniteTransition(label = "")
                            val titleOffset by titleTransition.animateValue(
                                initialValue = 0.dp,
                                targetValue = 25.dp,
                                typeConverter = Dp.VectorConverter,
                                animationSpec = remember {
                                    InfiniteRepeatableSpec(
                                        animation = tween(durationMillis = 2_000),
                                        repeatMode = RepeatMode.Reverse
                                    )
                                }, label = ""
                            )
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "为什么要使用动画？👉🏻",
                                    modifier = Modifier
                                        .offset {
                                            IntOffset(titleOffset.roundToPx(), 0)
                                        },
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colorScheme.inverseOnSurface
                                    )
                                )
                                Spacer(
                                    Modifier
                                        .fillMaxWidth()
                                        .weight(1f, false)
                                )
                                FilledIconButton(
                                    onClick = { expandMore = !expandMore },
                                    modifier = Modifier.graphicsLayer {
                                        rotationZ = buttonRotateAngle
                                    }
                                ) {
                                    Icon(Icons.Sharp.KeyboardArrowDown, contentDescription = null)
                                }
                            }
                        }
                        AnimatedVisibility(visible = expandMore) {
                            CompositionLocalProvider(
                                LocalTextStyle provides MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Column(Modifier.padding(10.dp)) {
                                    Text(
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        text = "动画是移动应用程序的基础，它为用户提供流畅的用户体验。",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text("🚀 增强的用户体验：动画使 UI 更具交互性和吸引力。")
                                    Text("👁 视觉反馈：提供视觉提示，使应用程序更加直观。")
                                    Text("📱 美观：精心设计的动画可以让应用脱颖而出。")
                                }
                            }

                        }

                    }
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        HorizontalDivider(
                            Modifier
                                .weight(1f, false)
                                .padding(end = 10.dp)
                        )
                        Text(
                            "码上开始", style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        HorizontalDivider(
                            Modifier
                                .weight(1f, false)
                                .padding(start = 10.dp)
                        )
                    }
                }
                item {
                    Text("动画目录分类：", style = MaterialTheme.typography.titleLarge)
                }
                item(key = "Value-based动画") {
                    CategoriesItem(title = { Text("Value-based动画") }, onClick = {})
                }
                item(key = "定制型动画Animatable") {
                    CategoriesItem(title = { Text("定制型动画Animatable") }, onClick = {})
                }
                item(key = "动画Modifier以及动画可组合项") {
                    CategoriesItem(title = { Text("动画Modifier、\n动画可组合项") }, onClick = {})
                }
            }
        }


    }

}

@Composable
private fun CategoriesItem(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier
            .fillMaxWidth()
    ) {
        val imageTransition = rememberInfiniteTransition(label = "")
        val imageRotate by imageTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = remember {
                InfiniteRepeatableSpec(
                    animation = tween(
                        durationMillis = 10_000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            }, label = ""
        )
        Row(
            Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.titleLarge
            ) {
                title()
            }
            Spacer(Modifier.width(10.dp))
            Icon(
                modifier = Modifier.graphicsLayer {
                    rotationZ = imageRotate
                },
                imageVector = Icons.Filled.Settings,
                contentDescription = null
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(1f, false)
            )
            FilledIconButton(onClick = onClick) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = null
                )
            }
        }

    }
}