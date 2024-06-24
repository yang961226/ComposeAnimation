package com.sundayting.composeanimation.ui.value_base

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sundayting.composeanimation.R

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

                item {
                    HorizontalDivider()
                }

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

        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
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
}