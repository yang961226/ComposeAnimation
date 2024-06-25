package com.sundayting.composeanimation.ui.value_base

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sundayting.composeanimation.R
import kotlinx.coroutines.delay

object HighLevelPage {

    const val ROUTE = "high_level"


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController,
    ) {

        Scaffold(modifier, topBar = {
            TopAppBar(
                title = { Text("高级动画概览") },
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
                item("1") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Card {
                            Text(
                                "📚 内建的动画Modifier：animateContentSize。在可组合项的Modifier链中，加入animateContent，可以实现可组合项大小发生变化的时候添加渐进的动画效果",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Text("下面的代码尝试在一个大小会变化的方块可组合项的Modifier链中添加animateContentSize修饰符，之后方块的大小发生变化时会伴有动画：")

                        Image(
                            painterResource(id = R.drawable.high_1),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text(
                            "上述的代码运行起来后的效果",
                            modifier = Modifier.align(Alignment.Start)
                        )

                        AnimateModifierExample()

                        Card {
                            Text(
                                "⚠️需要注意的是，animateContentSize()在Modifier链中，必须早于任何与大小相关的修饰符，因为只有这样才可以让animateContentSize()正确地向布局报告动画值。",
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                item {
                    HorizontalDivider()
                }

                item("2") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Card {
                            Text(
                                "📚 使用动画规范（AnimationSpec）定制animateContentSize动画",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.high_2),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("通过观察源码得知，animateContentSize()默认使用的是没有弹性效果的弹簧动画，可以通过这个修改默认值来定制不同的动画规范，下面是增加了弹性效果的弹簧动画，此时方块接近一个果冻：")

                        AnimateModifierExample(
                            spec = spring(
                                dampingRatio = Spring.DampingRatioHighBouncy
                            )
                        )
                    }
                }

                item {
                    HorizontalDivider()
                }

                item("3") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Card {
                            Text(
                                "📚 animateItemPlacement()，专门为Lazy列表制定的item动画。在Lazy列表中，为item的Modifier链中使用animateItemPlacement()方法，当item的位置发生变化时（或者进场出场），会伴有动画效果。",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.high_3),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        AnimateItemPlacementExample()
                    }
                }

                item { HorizontalDivider() }

                item("4") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Card {
                            Text(
                                "📚 Crossfade()，当需要从2个或多个布局切换的过程中添加渐隐式动画的时候，可以使用这个可组合项",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Text("下面简单看一个Crossfade()的用法，其聚焦于targetState，当不同的target发生变化的时候，Crossfade()会在不同的可组合项切换的中间添加渐隐动画。")
                        Image(
                            painter = painterResource(id = R.drawable.high_4),
                            contentScale = ContentScale.FillWidth,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("为了直观体验Crossfade使用前后的区别，下图展现了分别没有使用Crossfade()和不使用Crossfade()的区别：：")

                        CrossfadeExample()
                    }
                }

                item { HorizontalDivider() }

                item("5") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Card {
                            Text(
                                "📚 AnimatedVisibility()，与Crossfade()类似，不同的是这个可组合项聚焦单个可组合项消失\\出场的时候的动画。但是需要注意的是，当Visibility的目标值为false且动画运行结束的时候，其内部的可组合项会真正被移除，而不是单纯不可见。",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Image(
                            painterResource(id = R.drawable.high_5),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("下面的案例是使用AnimatedVisibility()实现的一个方块隐藏和显示的动画：")

                        AnimatedVisibilityExample()

                        Text("下面的代码是AnimatedVisibility()的使用方式，其中enter和exit指的是对应的可组合项的进场和出场方式，其代码大致如下：")

                        Image(
                            painterResource(id = R.drawable.high_6),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("上文提到，AnimatedVisibility()可以指定动画的运行方式，例如入场动画是从左到右，或者从上到下，亦或者是渐隐或渐显，开发者甚至可以合并他们，例如指定一个从上到下而且是渐现的动画，下面简单了解一下开发者可以修改的四种动画方式：")

                        val infiniteTransition = rememberInfiniteTransition(label = "")
                        val animatedProgress by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = remember {
                                InfiniteRepeatableSpec(
                                    animation = tween(easing = LinearEasing, durationMillis = 3000),
                                    repeatMode = RepeatMode.Restart
                                )
                            }, label = ""
                        )

                        val isVisible by remember {
                            derivedStateOf { animatedProgress > 0.5f }
                        }

                        Column(
                            Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "fade：渐隐和渐显",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f, false)
                                )
                                AnimatedVisibilityExample2(
                                    isVisible = isVisible,
                                    enterTransition = fadeIn(tween(1000)),
                                    exitTransition = fadeOut(tween(1000))
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "slide：滑入和滑出",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f, false)
                                )
                                AnimatedVisibilityExample2(
                                    isVisible = isVisible,
                                    enterTransition = slideInHorizontally(tween(1000)) { width -> -width },
                                    exitTransition = slideOutHorizontally(tween(1000)) { width -> width },
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "scale：比例大小收缩",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f, false)
                                )
                                AnimatedVisibilityExample2(
                                    isVisible = isVisible,
                                    enterTransition = scaleIn(tween(1000)),
                                    exitTransition = scaleOut(tween(1000)),
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "expand、shrink：展开和收起",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f, false)
                                )
                                AnimatedVisibilityExample2(
                                    isVisible = isVisible,
                                    enterTransition = expandHorizontally(tween(1000)),
                                    exitTransition = shrinkHorizontally(tween(1000)),
                                )
                            }

                        }


                        Text("💡 进场和出场动画不仅仅只有一种，开发者可以合并多个进场动画，使用+号，可以参考以下的方式合并多个动画：")

                        Image(
                            painterResource(id = R.drawable.high_7),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "合并滑动和渐显",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f, false)
                            )
                            AnimatedVisibilityExample2(
                                isVisible = isVisible,
                                enterTransition = fadeIn() + slideInHorizontally { width -> -width },
                                exitTransition = fadeOut() + slideOutHorizontally { width -> width }
                            )
                        }

                    }
                }

                item { HorizontalDivider() }

                item("6") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Card {
                            Text(
                                "📚 AnimatedContent()——定制化更高的强化版Crossfade()，不仅可以渐显渐隐，还可以定制任意的转移动画。",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.high_14),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("AnimatedContent()的Api设计和AnimatedVisibility()非常类似，开发者需要关注的是targetState和transitionSpec，其中transitionSpec是决定不同状态之间动画转移的方式，下图展示的是渐隐渐显+平移的方式：")

                        AnimatedContentExample()

                        Text("💡AnimateContent()的transitionSpec看起来稍微有点复杂，核心是决定入场和离场动画，先让我们回顾一下AnimatedVisibility()的设计：")

                        Image(
                            painter = painterResource(id = R.drawable.high_15),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("可以看出，在AnimatedVisibility()中开发者需要指定在两个参数中分别指定入场和离场动画，而在AnimateContent()中，开发者只需要通过一个参数transitionSpec指定，入场动画和离场动画通过一个中缀表达式「togetherWith」来拼凑它们即可。")

                        Image(
                            painter = painterResource(id = R.drawable.high_16),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("💡transitionSpec还可以使用using中缀表达式接入SizeTransform，这样可以定制切换过程中的Size变化，下图的代码实现了先让宽度达到最大，然后再继续展开高度的代码：")

                        Image(
                            painter = painterResource(id = R.drawable.high_17),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        AnimatedContentExample2()


                    }
                }

            }
        }
    }

    @Composable
    private fun AnimateModifierExample(
        modifier: Modifier = Modifier,
        spec: FiniteAnimationSpec<IntSize> = spring(
            stiffness = Spring.StiffnessMediumLow
        ),
    ) {

        var isBigSize by remember {
            mutableStateOf(false)
        }

        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.height(300.dp)
            ) {
                Box(
                    Modifier
                        .padding(20.dp)
                        .background(Color.Blue.copy(0.5f))
                        .animateContentSize(
                            animationSpec = spec
                        )
                        .fillMaxWidth(if (isBigSize) 1f else 0.3f)
                        .height(if (isBigSize) 300.dp else 150.dp)
                )
            }
            Button(onClick = { isBigSize = !isBigSize }) {
                Text("点我试试")
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun AnimateItemPlacementExample(
        modifier: Modifier = Modifier,
    ) {
        val list = remember {
            mutableStateListOf("A", "B", "C", "D", "E")
        }
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(list, key = { it }) {
                    Card(Modifier.animateItemPlacement()) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("我是$it")
                        }
                    }
                }
            }
            Spacer(Modifier.height(50.dp))

            Button(onClick = {
                val newList = list.shuffled()
                list.clear()
                list.addAll(newList)
            }) {
                Text("打乱顺序")
            }

            Spacer(Modifier.height(20.dp))

            Card {
                Text(
                    "⚠️需要注意的是，为了让动画生效，必须为item使用key，这是让Lazy可组合项能够正确识别item位置的前提。",
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

    }


    @Composable
    private fun CrossfadeExample(
        modifier: Modifier = Modifier,
    ) {

        var changeTag by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {

                Box(
                    Modifier
                        .fillMaxSize()
                        .weight(1f, false),
                    contentAlignment = Alignment.Center
                ) {
                    if (changeTag) {
                        CrossfadeExampleSub1()
                    } else {
                        CrossfadeExampleSub2()
                    }
                }

                VerticalDivider(Modifier.padding(horizontal = 10.dp))

                Crossfade(
                    targetState = changeTag, label = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f, false),
                ) { tag ->
                    if (tag) {
                        CrossfadeExampleSub1()
                    } else {
                        CrossfadeExampleSub2()
                    }
                }

            }

            Spacer(Modifier.height(20.dp))
            Button(onClick = { changeTag = !changeTag }) {
                Text("点我切换，当前:${changeTag}")
            }
        }

    }

    @Composable
    private fun CrossfadeExampleSub1(
        modifier: Modifier = Modifier,
    ) {

        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    @Composable
    private fun CrossfadeExampleSub2(
        modifier: Modifier = Modifier,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Blue.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(modifier = modifier, text = "加载完了")
        }

    }

    @Composable
    private fun AnimatedVisibilityExample(
        modifier: Modifier = Modifier,
    ) {

        var isVisible by remember {
            mutableStateOf(false)
        }


        Column(
            modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(visible = isVisible) {
                Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Red.copy(0.5f))
                )
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .weight(1f, false)
            )

            Spacer(Modifier.height(20.dp))
            Button(onClick = { isVisible = !isVisible }) {
                Text("切换")
            }

        }

    }

    @Composable
    private fun AnimatedVisibilityExample2(
        modifier: Modifier = Modifier,
        isVisible: Boolean,
        enterTransition: EnterTransition = fadeIn() + expandIn(),
        exitTransition: ExitTransition = shrinkOut() + fadeOut(),
    ) {
        Column(
            modifier
                .size(100.dp)
                .border(1.dp, Color.Red),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                modifier = modifier,
                visible = isVisible,
                enter = enterTransition,
                exit = exitTransition
            ) {
                Column(
                    Modifier
                        .size(100.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f, false)
                            .background(Color.Red.copy(0.2f))
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f, false)
                            .background(Color.Blue.copy(0.2f))
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f, false)
                            .background(Color.Green.copy(0.2f))
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f, false)
                            .background(Color.Magenta.copy(0.2f))
                    )
                }
            }
        }
    }

    @Composable
    private fun AnimatedContentExample(
        modifier: Modifier = Modifier,
    ) {

        var index by remember {
            mutableIntStateOf(0)
        }

        LaunchedEffect(Unit) {
            while (true) {
                delay(2000)
                index = (index + 1) % 3
            }
        }

        AnimatedContent(
            targetState = index,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f), label = "",
            transitionSpec = {
                fadeIn() + slideInHorizontally { width -> width } togetherWith fadeOut() + slideOutHorizontally { width -> -width }
            }
        ) { i ->
            Image(
                painter = painterResource(
                    id = when (i) {
                        0 -> R.drawable.high_10
                        1 -> R.drawable.high_11
                        2 -> R.drawable.high_12
                        else -> R.drawable.high_13
                    }
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

    }

    @Composable
    private fun AnimatedContentExample2(
        modifier: Modifier = Modifier,
    ) {

        var expanded by remember { mutableStateOf(false) }

        Box(modifier.height(400.dp)) {
            Card(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) { expanded = !expanded }
            ) {
                AnimatedContent(
                    modifier = Modifier.padding(10.dp),
                    targetState = expanded,
                    transitionSpec = {
                        (fadeIn(
                            tween(durationMillis = 300)
                        ) togetherWith fadeOut(
                            tween(durationMillis = 300)
                        )) using SizeTransform { initSize, targetSize ->
                            //如果是展开的情况，在动画进行到一半之前，高度保持不变，让宽度变化
                            if (targetState) {
                                keyframes {
                                    IntSize(targetSize.width, initSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                            //如果是收缩的情况，在动画进行一半之前，宽度保持不变，让高度变化
                            else {
                                keyframes {
                                    IntSize(initSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
                    },
                    label = ""
                ) { targetExpanded ->
                    if (targetExpanded) {
                        Text(
                            "话说是日贾敬的寿辰，贾珍先将上等可吃的东西、稀奇些的果品，装了十六大捧盒，着贾蓉带领家下人等与贾敬送去，向贾蓉说道：“你留神看太爷喜欢不喜欢，你就行了礼来。你说：‘我父亲遵太爷的话未敢来，在家里率领合家都朝上行了礼了。’”贾蓉听罢，即率领家人去了。这里渐渐的就有人来了。先是贾琏、贾蔷到来，先看了各处的座位，并问：“有什么玩意儿没有？”家人答道：“我们爷原算计请太爷今日来家，所以并未敢预备顽意儿。前日，听见太爷又不来了，现叫奴才们找了一班小戏儿并一档子打十番的，都在园子里戏台上预备着呢。”",
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Icon(imageVector = Icons.Default.Call, contentDescription = null)
                    }
                }
            }

        }

    }
}