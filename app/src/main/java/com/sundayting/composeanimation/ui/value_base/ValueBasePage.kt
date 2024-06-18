package com.sundayting.composeanimation.ui.value_base

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
                item("3.1") {
                    Block3_1()
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
                        Card {
                            Text(
                                "📚 AnimationSpec(the specification of an animation)，动画规格",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Text(
                            "动画规格（Spec）影响了动画在往目标值移动的过程中的具体运行逻辑，不同的Spec可以让动画产生不同的效果，下面通过实际案例看看他们的差异"
                        )

                        var toTarget by remember {
                            mutableStateOf(false)
                        }

                        Column(
                            modifier = Modifier.padding(vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("tween（补间动画——快进慢出）")
                            AnimateDpExampleRow(
                                toTarget = toTarget,
                                animationSpec = tween(
                                    durationMillis = 500
                                )
                            )
                            Text("tween（补间动画——线性）")
                            AnimateDpExampleRow(
                                toTarget = toTarget,
                                animationSpec = tween(
                                    easing = LinearEasing,
                                    durationMillis = 500
                                )
                            )
                            Text("keyframe（关键帧动画）")
                            AnimateDpExampleRow(
                                toTarget = toTarget,
                                animationSpec = if (toTarget) {
                                    keyframes {
                                        durationMillis = 500
                                        0.dp at 0 using LinearOutSlowInEasing // for 0-15 ms
                                        50.dp at 250 using FastOutLinearInEasing // for 15-75 ms
                                        100.dp at 400 // ms
                                        150.dp at 500 // ms
                                    }
                                } else {
                                    keyframes {
                                        durationMillis = 500
                                        150.dp at 0 using LinearOutSlowInEasing // for 0-15 ms
                                        100.dp at 250 using FastOutLinearInEasing // for 15-75 ms
                                        50.dp at 400 // ms
                                        0.dp at 500 // ms
                                    }
                                }
                            )
                            Text("弹簧动画")
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
                        Box(Modifier.height(10.dp))
                        Card {
                            Column(Modifier.padding(10.dp)) {

                                Text(
                                    "🚀 1、tween",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.align(Alignment.Start)
                                )
                                Text(
                                    "       tween必须在规定的时间内完成，它的动画效果是基于时间参数计算的，可以使用 Easing 来指定不同的时间曲线动画效果。可以使用 tween() 方法进行创建。"
                                )
                            }
                        }

                        Spacer(Modifier.height(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.valuebase_3_2),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Text(
                            """
                                
                                💡「durationMillis」表示动画的持续时间。
                                
                                💡「delayMillis」表示动画延迟时间。
                                
                                💡「easing」 动画曲线变化，默认值是FastOutSlowIn（先快后慢）。
                                
                            """.trimIndent(),
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Text("tween的参数中，值得一提的是easing参数，这是补间动画的核心，它决定了补间动画的时间与运行速率的关系，下面从代码上解释：")
                        Image(
                            painter = painterResource(id = R.drawable.valuebase_3_4),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Text(
                            "Easing是一个接口，它的意图是绑定动画的百分比与动画的速率的关系，如何直接返回fraction，则表示动画是线性的匀速运动。",
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.valuebase_3_3),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Text(
                            "Compose已经默认提供了几种默认的Easing，它们分别是FastOutSlowInEasing（快进慢出），LinearOutSlowInEasing（匀速进慢出），FastOutLinearInEasing（快进匀速出），LinearEasing（匀速）",
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .align(Alignment.Start),
                        )

                        Card {
                            Column(Modifier.padding(10.dp)) {
                                Text(
                                    "⚠️额外知识：以上几种Easing的均使用了贝塞尔曲线，关于贝塞尔曲线的知识读者可以自行学习。",
                                )
                                val context = LocalContext.current
                                Button(
                                    onClick = {
                                        context.startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("https://cubic-bezier.com/")
                                            )
                                        )
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text("我想体验贝塞尔曲线")
                                }
                            }
                        }


                        Text(
                            "另外，开发者还可以通过传入Path的方式构建Easing，或者直接根据数学函数构建Easing（直接实现Easing接口），不过这种开发模式比较少。",
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.valuebase_3_5),
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(),
                            contentDescription = null
                        )

                        Card(Modifier.padding(vertical = 20.dp)) {
                            Text(
                                "✈️如何合理根据场景使用不同的Easing呢？首先，大多数情况下是不会使用线性动画，因为自然中线性的东西会有很强烈的人造感，为了提升用户体验，往往使用的是快进慢出这种动画，或者使用弹性动画（下文会讲），因为这种动画可以模拟事物逐渐减速的感觉。",
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Column(
                            Modifier
                                .align(Alignment.Start)
                                .fillMaxWidth()
                        ) {
                            Text("快进慢出——FastOutSlowIn")
                            Text(
                                "模拟物体被抛进来，最后慢慢停下来的感觉",
                                style = MaterialTheme.typography.bodySmall
                            )

                            var fastOutSlowInTarget by remember {
                                mutableStateOf(false)
                            }

                            FastOutSlowInExample(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 40.dp, bottom = 20.dp),
                                toTarget = fastOutSlowInTarget
                            )

                            Button(
                                onClick = { fastOutSlowInTarget = !fastOutSlowInTarget },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("点我开始")
                            }

                            HorizontalDivider(Modifier.padding(vertical = 20.dp))

                            Text("匀速——Linear")
                            Text(
                                "全程匀速，比较缺乏生气",
                                style = MaterialTheme.typography.bodySmall
                            )

                            var linearTarget by remember {
                                mutableStateOf(false)
                            }

                            LinearTweenExample(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 40.dp, bottom = 20.dp), toTarget = linearTarget
                            )

                            Button(
                                onClick = { linearTarget = !linearTarget },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("点我开始")
                            }

                        }

                        Card(Modifier.padding(vertical = 20.dp)) {
                            Column(Modifier.padding(10.dp)) {

                                Text(
                                    "🚀 2、keyframe",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.align(Alignment.Start)
                                )
                                Text(
                                    "       基于时间的动画规格，在不同的时间戳定义值，更精细地来实现关键帧的动画。可以使用 keyframes() 方法来创建 KeyframesSpec。"
                                )

                            }
                        }

                        Text("当业务上需要：某个时间段内以某种动画规格，某个时间段内使用另外某种动画规格时，比较适合使用关键帧动画——keyframe。\n下面以一个前期匀速，中期加速再减速，后期匀速的动画来演示：")

                        var keyFrameTarget by remember {
                            mutableStateOf(false)
                        }

                        KeyFrameExample(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 40.dp, bottom = 20.dp),
                            toTarget = keyFrameTarget
                        )

                        Button(
                            onClick = { keyFrameTarget = !keyFrameTarget },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("点我开始")
                        }

                        Spacer(Modifier.height(20.dp))

                        Column(
                            Modifier.align(Alignment.Start),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("keyframe的设置稍微绕一点，让我们看一看上面动画的keyframe是如何设置的")
                            Image(
                                painter = painterResource(id = R.drawable.valuebase_3_6),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text("首先通过durationMills设置整个动画的运行时间，然后通过「at」和「using」两个中缀表达式来分别设置时间区间和区间对应的Easing。\n\n然而令人困惑的是，at后面接着的是一个时间点，并不是时间段，那么at 0指的是哪个时间段呢，实际上在keyframe的设置中，at 0指的是「设置的时间段是0到下一个时间段」这个时间段，因此上述代码指的是0-1000这个时间段。\n\n因此，上述的代码中，0-1秒的时间段设置了线性，1-2秒的时间段设置了快进慢出，2-3秒的时间段设置了线性。")

                            Card {
                                Text(
                                    "⚠️与tween不同的是，由于keyframe的设置过程中，时间段和运行的目标是绑定的，因此不能很好支持动画的逆向，如果想支持运行过程逆向的keyframe动画，只能反着写一段，参考下面的代码：",
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.valuebase_3_7),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                    }
                }
            }
        }

    }

    @Composable
    private fun Block3_1() {
        Column {
            Card {
                Text(
                    "📚 下面从animateDpAsState()了解如何使用如何使用animate*AsState()",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.valuebase_3_1),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Text(
                """
                
                💡「targetValue」表示动画的目标值。
                
                💡「animationSpec」 动画规格，决定了动画的执行逻辑。
                
                💡「label」 这个参数是为了区别在 Android Studio 中进行动画预览时，区别其它动画的。
                
                💡「finishedListener 」可以用来监听动画的结束。
                
            """.trimIndent()
            )
            Text("通常情况下，开发者只需要关注「targetValue」和「animationSpec」即可，上文中填入的值即targetValue，而animationSpec暂不展开，下文会集中讨论。")
        }

    }

    @Composable
    private fun FastOutSlowInExample(
        modifier: Modifier = Modifier,
        toTarget: Boolean,
    ) {
        val offsetDp by animateDpAsState(
            targetValue = if (toTarget) 150.dp else 0.dp, label = "",
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
        val alpha by animateFloatAsState(
            targetValue = if (toTarget) 1f else 0f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing), label = ""
        )
        Box(
            modifier
                .height(50.dp)
                .width(200.dp)
        ) {
            Box(
                Modifier
                    .offset {
                        IntOffset(offsetDp.roundToPx(), 0)
                    }
                    .graphicsLayer {
                        this.alpha = alpha
                    }
                    .size(50.dp)
                    .background(Color.Red.copy(0.2f))
            )
        }
    }

    @Composable
    private fun LinearTweenExample(
        modifier: Modifier = Modifier,
        toTarget: Boolean,
    ) {
        val offsetDp by animateDpAsState(
            targetValue = if (toTarget) 150.dp else 0.dp, label = "",
            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
        )
        val alpha by animateFloatAsState(
            targetValue = if (toTarget) 1f else 0f,
            animationSpec = tween(durationMillis = 500, easing = LinearEasing), label = ""
        )
        Box(
            modifier
                .height(50.dp)
                .width(200.dp)
        ) {
            Box(
                Modifier
                    .offset {
                        IntOffset(offsetDp.roundToPx(), 0)
                    }
                    .graphicsLayer {
                        this.alpha = alpha
                    }
                    .size(50.dp)
                    .background(Color.Red.copy(0.2f))
            )
        }
    }

    @Composable
    private fun KeyFrameExample(
        modifier: Modifier = Modifier,
        toTarget: Boolean,
    ) {

        val offsetDp by animateDpAsState(
            targetValue = if (toTarget) 150.dp else 0.dp, label = "",
            animationSpec = if (toTarget) {
                keyframes {
                    durationMillis = 3000
                    0.dp at 0 using LinearEasing
                    50.dp at 1000 using FastOutSlowInEasing
                    100.dp at 2000 using LinearEasing
                }
            } else {
                keyframes {
                    durationMillis = 3000
                    150.dp at 0 using LinearEasing
                    100.dp at 1000 using FastOutSlowInEasing
                    50.dp at 2000 using LinearEasing
                }
            }
        )
        Box(
            modifier
                .height(50.dp)
                .width(200.dp)
        ) {
            Box(
                Modifier
                    .offset {
                        IntOffset(offsetDp.roundToPx(), 0)
                    }
                    .size(50.dp)
                    .background(Color.Red.copy(0.2f))
            )
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
                        IntOffset(offsetDp.roundToPx(), 0)
                    }
                    .size(50.dp)
                    .background(Color.Red.copy(0.2f))
            )
        }
    }

    @Composable
    private fun Block4() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card {
                Text(
                    "📚 animate*AsState的秘密：与MutableState Api类似的设计，我们对比一下两者",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Text(
                "var value by remember { mutableStateOf(默认值) }",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    )
            )
            Text(
                "val value by animate*AsState(最新值)",
                modifier = Modifier.padding(10.dp)
            )
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
            Card {
                Column(Modifier.padding(10.dp)) {
                    Text(
                        "📚 animate*AsState 函数的使用方式非常简单，只需要遵循下面的范式即可：",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        "val value by animate*AsState(最新值)",
                        modifier = Modifier.padding(10.dp)
                    )
                }

            }

            Spacer(Modifier.height(20.dp))
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
            Card {
                Text(
                    "📚 animate*AsState函数是 Compose 中最简单的动画 API，用于对单个值进行动画处理。\n您只需提供目标值（或最终值），API 就会开始从当前值到指定值的动画。",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(Modifier.height(20.dp))
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