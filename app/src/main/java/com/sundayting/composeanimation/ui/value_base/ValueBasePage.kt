package com.sundayting.composeanimation.ui.value_base

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sundayting.composeanimation.R

object ValueBasePage {

    const val ROUTE = "value_base_page"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController,
    ) {


        Scaffold(modifier, topBar = {
            TopAppBar(
                title = { Text("Value-Base动画") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
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
                item("1") {
                    Text(
                        "📚 animate*AsState函数是 Compose 中最简单的动画 API，用于对单个值进行动画处理。\n您只需提供目标值（或最终值），API 就会开始从当前值到指定值的动画。",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                item("2") {
                    Block2()
                }
                item {
                    HorizontalDivider()
                }
                item("3") {
                    Block3()
                }
                item {
                    HorizontalDivider()
                }
                item("4") {
                    Block4()
                }
                item {
                    HorizontalDivider()
                }
                item("5") {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "📚 上面只提到了如何使用animate*AsState()来为不同状态之间的迁移提供最新的动画值，但是动画的「差值」如何修改的呢？在讲这个之前，先通过几个不同的案例来对比不同差值的区别：",
                            style = MaterialTheme.typography.titleMedium
                        )

                        var toTarget by remember {
                            mutableStateOf(false)
                        }

                        Column(
                            modifier = Modifier.padding(vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            AnimateDpExampleRow(
                                toTarget = toTarget,
                                animationSpec = tween(
                                    durationMillis = 500
                                )
                            )
                            AnimateDpExampleRow(
                                toTarget = toTarget,
                                animationSpec = tween(
                                    easing = LinearEasing,
                                    durationMillis = 500
                                )
                            )
                            AnimateDpExampleRow(
                                toTarget = toTarget,
                                animationSpec = if(toTarget){
                                    keyframes {
                                        durationMillis = 500
                                        0.dp at 0 using LinearOutSlowInEasing // for 0-15 ms
                                        50.dp at 250 using FastOutLinearInEasing // for 15-75 ms
                                        100.dp at 400 // ms
                                        150.dp at 500 // ms
                                    }
                                }else{
                                    keyframes {
                                        durationMillis = 500
                                        150.dp at 0 using LinearOutSlowInEasing // for 0-15 ms
                                        100.dp at 250 using FastOutLinearInEasing // for 15-75 ms
                                        50.dp at 400 // ms
                                        0.dp at 500 // ms
                                    }
                                }
                            )
                            AnimateDpExampleRow(
                                toTarget = toTarget,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }

                        Button(onClick = { toTarget = !toTarget }) {
                            Text("点我开始动画")
                        }
                    }
                }
            }
        }

    }


    @Composable
    private fun AnimateDpExampleRow(
        modifier: Modifier = Modifier,
        toTarget: Boolean,
        animationSpec: AnimationSpec<Dp>,
    ) {
        val offsetDp by animateDpAsState(
            targetValue = if (toTarget) 150.dp else 0.dp, label = "",
            animationSpec = animationSpec
        )
        Box(
            modifier
                .height(50.dp)
                .width(200.dp)
                .background(Color.LightGray)
        ) {
            Box(
                Modifier
                    .offset {
                        IntOffset(offsetDp.roundToPx(),0)
                    }
                    .size(50.dp)
                    .background(Color.Red.copy(0.2f))
            )
        }
    }

    @Composable
    private fun Block4() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "📚 animate*AsState的秘密：与MutableState Api类似的设计，我们对比一下两者",
                style = MaterialTheme.typography.titleMedium,

                )
            Card(Modifier.padding(10.dp)) {
                Text(
                    "var value by remember { mutableStateOf(默认值) }",
                    modifier = Modifier
                        .padding(10.dp)
                        .horizontalScroll(
                            rememberScrollState()
                        )
                )
            }
            Card(Modifier.padding(10.dp)) {
                Text(
                    "val value by animate*AsState(最新值)",
                    modifier = Modifier.padding(10.dp)
                )
            }
            Text(
                "可以看出，两者是非常类似的，也就是说再对组件的动画改造过程中，开发者只需要修改上流的实现即可（只需要修改value的实现），而在下流的组件取值的时候，并不需要做「任何改动」，这极大提高了开发速度。"
            )
            Text("下面提供一个对照，分别是使用了动画和不使用动画的组件差异，可以比较动画对用户体验的提升：")
            Row(
                Modifier
                    .padding(top = 15.dp)
                    .height(IntrinsicSize.Min)
            ) {
                var big1 by remember {
                    mutableStateOf(false)
                }
                Button(
                    onClick = { big1 = !big1 },
                    shape = RoundedCornerShape(if (big1) 20.dp else 0.dp)
                ) {
                    Text("无动画")
                }
                VerticalDivider(
                    Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxHeight()
                )
                var big2 by remember {
                    mutableStateOf(false)
                }
                val cornerSize by animateDpAsState(
                    targetValue = if (big2) 20.dp else 0.dp,
                    label = ""
                )
                Button(
                    onClick = { big2 = !big2 },
                    shape = RoundedCornerShape(cornerSize)
                ) {
                    Text("有动画")
                }
            }
        }
    }

    @Composable
    private fun Block3() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "📚 animate*AsState 函数的使用方式非常简单，只需要遵循下面的范式即可：",
                style = MaterialTheme.typography.titleMedium
            )
            Card(Modifier.padding(10.dp)) {
                Text(
                    "val value by animate*AsState(最新值)",
                    modifier = Modifier.padding(10.dp)
                )
            }
            Text("按照业务的要求，填入不同的状态值，animate*AsState就会按照动画的要求，输出最新的value，直到value达到最新值")
            Box(Modifier.height(10.dp))

            Text("下面用矩形的圆角来演示，以animateDpAsState(if(big) 20.dp else 0.dp)的代码来控制矩形的圆角，其中big就是一个布尔状态值：")


            var big by remember {
                mutableStateOf(false)
            }
            val animateCornerSize by animateDpAsState(
                targetValue = if (big) 20.dp else 0.dp,
                label = "圆角大小"
            )
            Button(
                onClick = { big = !big },
                shape = RoundedCornerShape(animateCornerSize),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text("点击我试试 圆角目前是「${if (big) "大" else "小"}」")
            }
        }
    }

    @Composable
    private fun Block2() {
        val infiniteTransition = rememberInfiniteTransition(label = "")
        //为下面的动画提供一个进度种子
        val infiniteTransitionSeed by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 100f,
            animationSpec = remember {
                InfiniteRepeatableSpec(
                    animation = tween(
                        durationMillis = 3000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            },
            label = ""
        )
        Column {
            Text("下面展示的是支持不同类型的animate*AsState的方法，其中*就是对应的类型")
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            SpanStyle(fontSize = 20.sp)
                        ) {
                            append("animate")
                        }
                        withStyle(
                            SpanStyle(fontSize = 20.sp)
                        ) {
                            append("*")
                        }
                        withStyle(
                            SpanStyle(fontSize = 20.sp)
                        ) {
                            append("AsState()")
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 20.dp, start = 30.dp)
                )

                val animateColor by animateColorAsState(
                    targetValue =
                    (if (infiniteTransitionSeed < 20) Color.Red else if (infiniteTransitionSeed < 50) Color.Blue else if (infiniteTransitionSeed < 70) Color.Green else Color.Magenta).copy(
                        0.5f
                    ), label = ""
                )

                Text(
                    "animateColorAsState()",
                    style = MaterialTheme.typography.bodyLarge.copy(color = animateColor),
                    modifier = Modifier.padding(top = 100.dp, start = 30.dp)
                )

                val animateDp by animateDpAsState(
                    targetValue = (infiniteTransitionSeed / 10).dp,
                    label = ""
                )

                Text(
                    "animateDpAsState()",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 70.dp, end = 30.dp + animateDp)
                )

                val animateSize by with(LocalDensity.current) {
                    animateSizeAsState(
                        targetValue = Size(
                            (infiniteTransitionSeed * 1).dp.toPx(),
                            (infiniteTransitionSeed * 0.5).dp.toPx()
                        ), label = ""
                    )
                }
                val animateDpSize =
                    with(LocalDensity.current) { animateSize.toDpSize() }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 150.dp, start = 50.dp)
                ) {
                    Box(
                        Modifier
                            .size(animateDpSize)
                            .background(Color.LightGray)
                    ) {
                    }
                    Text("animateSizeAsState()")
                }

                val animateFloat by animateFloatAsState(
                    targetValue = infiniteTransitionSeed / 100f * 360f,
                    label = ""
                )

                Row(
                    Modifier
                        .padding(top = 200.dp)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(id = R.drawable.icon_fan),
                        contentDescription = null,
                        modifier = Modifier

                            .size(50.dp)
                            .graphicsLayer {
                                rotationZ = animateFloat
                            }

                    )
                    Text("animateFloatAsState()")
                }


            }
        }
    }

}