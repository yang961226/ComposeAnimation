package com.sundayting.composeanimation.ui.value_base

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sundayting.composeanimation.R
import kotlinx.coroutines.delay

object AnimatablePage {

    const val ROUTE = "animatable_page"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController,
    ) {
        Scaffold(modifier, topBar = {
            TopAppBar(
                title = { Text("流程定制型动画") },
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
                    Column {
                        Card(Modifier.padding(vertical = 10.dp)) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "📚 Animatable是一种更底层的动画Api，上一节说到的animate*AsState的底层实现就是基于Animatable。",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                        }

                        Text("通过查看animateValueAsState的源码来确认这一点：")
                        Image(
                            painterResource(id = R.drawable.animatable_2),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("让我们简单看一下这个Api的使用，下面代码创建了一个Dp类型的Animatable")

                        Image(
                            painter = painterResource(id = R.drawable.animatable_3),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("代码中，创建了一个类型为Dp的Animatable，后面接了一个Compose默认实现的关于Dp的VectorConverter，这个VectorConverter就是上一节提到的「TwoWayConverter」，因此这是一个Dp与Float互转的Converter。")

                        Text("\n 🚀与animateDpAsState()不同的一点是：Animatable需要使用remember，并且需要赋予一个默认值。")

                        Text("\n 🚀另外不同的一点是，Animatable需要开发者手动去执行动画，而animatableValueAsState()只需要传入新的状态值就会自动执行动画并输出最新的状态值")

                        Text("\n 🚀下面给一个基于Animatable实现的圆角变化的动画及其源代码")



                        AnimatableExample(
                            Modifier.padding(vertical = 10.dp)
                        )
                        Text("其源码如下：", modifier = Modifier.padding(vertical = 10.dp))

                        Image(
                            painter = painterResource(id = R.drawable.animatable_4),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("可以看出，Animatable的使用有两点需要注意的：\n\n🚀 1、必须在协程中，调用其animateTo方法来转移到下一个状态。\n\n🚀 2、获取最新的动画值需要访问其value属性。")

                        Spacer(Modifier.height(20.dp))

                        Card {
                            Column(Modifier.padding(10.dp)) {
                                Text("那么开发者应该在何时使用这个更加底层的api呢？")
                                Image(
                                    painterResource(id = R.drawable.animatable_1),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .size(100.dp)
                                        .align(Alignment.CenterHorizontally),
                                )
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Text("在进一步探讨之前，我们先回顾一下animate*AsState()这个api，开发者是通过切换它的「目标值」来启动动画的，也就是说，当开发所需的场景是「在不同状态之间切换时，需要动画过渡」下的时候，用animate*AsState()可以非常方便开发者去实现，实际上这个api就是为了针对这个场景对Animatable进行了封装。")

                        Text("\n但是某些场景下并不是状态之间的切换，而是「包含了更为复杂的因素」时，用animate*AsState()会比较困难，例如「等待2秒后，再实现状态A到状态B的迁移」这种场景再去使用animate*AsState()会比较棘手。\n\n这个场景下开发者使用更为底层的Animatable则比较方便，下面的案例是一个等待1秒后才会变色的案例，实现也非常简单，只需要在协程启动时delay一秒即可。")

                        AnimatableExample2(
                            Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
                item{
                    HorizontalDivider()
                }
                item("2"){
                    Card {
                        Column(
                            Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🎉", style = TextStyle(fontSize = 50.sp))
                            Text("恭喜你读者，你已经完成这一节的所有内容，请根据本章的代码案例自行练习，或者返回并阅读下一章的内容")
                            Button(onClick = { navHostController.popBackStack() }) {
                                Text("返回")
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun AnimatableExample(
        modifier: Modifier = Modifier,
    ) {

        var big by remember {
            mutableStateOf(false)
        }

        val dpAnimatable = remember {
            Animatable(10.dp, Dp.VectorConverter)
        }

        LaunchedEffect(big) {
            dpAnimatable.animateTo(
                if (big) 50.dp else 10.dp
            )
        }

        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .padding(20.dp)
                    .size(100.dp)
                    .background(
                        Color.Red.copy(0.2f),
                        RoundedCornerShape(dpAnimatable.value)
                    )
            )

            Button(onClick = { big = !big }) { Text("点我变化") }
        }
    }

    @Composable
    private fun AnimatableExample2(
        modifier: Modifier = Modifier,
    ) {

        var tag by remember {
            mutableStateOf(false)
        }

        val dpAnimatable = remember {
            Animatable(Color.Red.copy(0.2f))
        }

        LaunchedEffect(tag) {
            delay(1000L)
            dpAnimatable.animateTo(
                if (tag) Color.Blue.copy(0.2f) else Color.Red.copy(0.2f)
            )
        }

        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .padding(20.dp)
                    .size(100.dp)
                    .background(
                        dpAnimatable.value,
                    )
            )

            Button(onClick = { tag = !tag }) { Text("点我1秒后变化") }
        }
    }

}