package com.sundayting.composeanimation.ui.value_base

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
                    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        Card {
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

                        Text("通过查看animateValueAsState的源码来确认这一点，可以看出，animateValueAsState()实际上是使用Animatable来实现的：")
                        Image(
                            painterResource(id = R.drawable.animatable_2),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
                item{
                    HorizontalDivider()
                }
                item("2"){
                    Column(verticalArrangement = Arrangement.spacedBy(20.dp)){
                        Card {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "📚 下面看看Animatable的api的设计，需要注意的是，Animatable是一个类，开发者需要调用它的animateTo()方法才能使它执行动画",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                        }

                        Text("下图展示的是Animatable类的源码：")

                        Image(
                            painter = painterResource(id = R.drawable.animatable_5),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text(
                            """
                
                💡「initialValue」表示动画的初始值，这是与animate*AsState()最大不同的地方，Animatable需要写入动画的初始值。
                
                💡「typeConverter」 上一章已经解释，为了给不同类型的动画适配，需要一个转换器将其他的类转成底层的浮点类型。
                
                💡「visibilityThreshold 」可见阈值，当动画低于当前阈值时自动停下，避免性能浪费。
                
                💡「label」 这个参数是为了区别在 Android Studio 中进行动画预览时，区别其它动画的。
                
            """.trimIndent()
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
                            Column(
                                Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Text("那么开发者应该在何时使用这个更加底层的api呢？")
                                Image(
                                    painterResource(id = R.drawable.animatable_1),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .size(100.dp)
                                        .align(Alignment.CenterHorizontally),
                                )
                                Text("要回答这个问题，我们先回到animate*AsState()本身，其实这个api就是对Animatable的「场景化封装」，这里指的是「不同状态下切换」的场景，当开发者需要面临的情况是状态不明确，亦或是开始状态和结束状态均为一致的这种例外情况，使用这个api将会导致开发者陷入困境，因此开发者需要使用更底层的api来解决他们的问题")
                            }
                        }
                    }
                }
                item {
                    HorizontalDivider()
                }
                item("结束") {
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
}