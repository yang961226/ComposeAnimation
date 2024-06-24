package com.sundayting.composeanimation.ui.value_base

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sundayting.composeanimation.R

object TransitionPage {

    const val ROUTE = "transition_page"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        modifier: Modifier = Modifier,
        navHostController: NavHostController,
    ) {

        Scaffold(modifier, topBar = {
            TopAppBar(
                title = { Text("多属性状态切换") },
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
                        Card {
                            Text(
                                "📚 在实际项目中，经常遇到一种场景，一个组件发生的动画是多个属性同时发生变化的，单纯使用animate*AsState()，不仅需要声明多个State，而且不利于状态的聚合，这个时候就需要使用Transition，作为多个变化属性的「种子」。",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Text(
                            "看看如果单纯使用Animatable是如何实现多个状态的变化：",
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                        Image(
                            painterResource(id = R.drawable.transition_1),
                            modifier = Modifier.fillMaxWidth(),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )

                        Text("在这种开发范式下，开发者需要启动多个协程来让动画同时启动，但「updateTransition」api是专门针对这种场景设计的，开发者只需要创建一个Transition，再通过Transition来创建多个动画，这些动画的启停统一由Transition控制：")

                        Image(
                            painterResource(id = R.drawable.transition_2),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )

                        Text(
                            "下面的案例是通过单个Transition控制多个动画状态（与上文代码无关）：",
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        Card {
                            TransitionExample(Modifier.padding(10.dp))
                        }
                    }
                }

                item { HorizontalDivider() }

                item("2") {
                    Column {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Card {
                                Text(
                                    "📚 监听Transition的运行状态，利用「updateTransition」的另外一个多态函数，实现对Transition运行状态的监听。",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }

                            Text("除了直接传入目标值以外，还可以通过「MutableTransitionState」的包装类间接传入目标值，借助这个包装类，开发者可以实现对Transition当前运行状态的监听：")

                            Image(
                                painter = painterResource(id = R.drawable.transition_3),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.FillWidth
                            )

                            Text("上述的代码运行情况如下：")

                            TransitionExample2()
                        }
                    }
                }

                item { HorizontalDivider() }

                item("3") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Card {
                            Text(
                                "📚 封装并复用Transition动画。使用 updateTransition 方法操作动画，没有问题，现在假设某个动画效果很复杂，我们不希望每次用的时候都去重新实现一遍，我们希望将上述动画效果封装起来，并可以复用。如何做呢？",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Text("下面以一个大小和颜色都会变化的动画为例，实现一个封装：")

                        Image(
                            painter = painterResource(id = R.drawable.transition_4),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("定义动画，并返回对应的值")

                        Image(
                            painter = painterResource(id = R.drawable.transition_5),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("使用封装好的TransitionData，这样可以实现对Transition的封装和复用")

                        Image(
                            painter = painterResource(id = R.drawable.transition_6),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Text("最后看看这个动画的效果")

                        TransitionExample3()
                    }
                }

                item("4"){
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
    private fun TransitionExample(modifier: Modifier = Modifier) {

        var tag by remember { mutableStateOf(false) }

        val animateTransition = updateTransition(tag, label = "")

        val size by animateTransition.animateDp(label = "") { big ->
            if (big) {
                200.dp
            } else {
                100.dp
            }
        }

        val conorDp by animateTransition.animateDp(label = "") { big ->
            if (big) {
                70.dp
            } else {
                20.dp
            }
        }

        val color by animateTransition.animateColor(label = "") { big ->
            if (big) {
                Color.Red.copy(0.2f)
            } else {
                Color.Blue.copy(0.2f)
            }
        }

        val rotate by animateTransition.animateFloat(label = "") { big ->
            if (big) {
                180f
            } else {
                0f
            }
        }

        Box(
            modifier
                .fillMaxWidth()
                .aspectRatio(1f), contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .graphicsLayer { rotationZ = rotate }
                    .size(size)
                    .background(color, RoundedCornerShape(conorDp)),
                contentAlignment = Alignment.Center
            ) {
                Text("😁", style = TextStyle(fontSize = 50.sp))
            }

            Button(onClick = { tag = !tag }, modifier = Modifier.align(Alignment.BottomCenter)) {
                Text("点我试试")
            }
        }


    }

    private enum class BoxState(val alias: String) {
        Small("小"),
        Medium("中"),
        Big("大")
    }

    @Composable
    private fun TransitionExample2(
        modifier: Modifier = Modifier,
    ) {

        val currentState = remember { MutableTransitionState(BoxState.Small) }

        val transition = updateTransition(transitionState = currentState, label = "")

        val sizeDp by transition.animateDp(label = "", transitionSpec = {
            spring()
        }) { boxState ->
            when (boxState) {
                BoxState.Small -> 50.dp
                BoxState.Medium -> 80.dp
                BoxState.Big -> 120.dp
            }
        }

        val color by transition.animateColor(label = "") { boxState ->
            when (boxState) {
                BoxState.Small -> Color.Blue.copy(0.2f)
                BoxState.Medium -> Color.Red.copy(0.2f)
                BoxState.Big -> Color.Green.copy(0.2f)
            }
        }

        Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 1f), contentAlignment = Alignment.Center
            ) {

                Box(
                    Modifier
                        .background(color, CircleShape)
                        .size(sizeDp)
                )


            }
            Spacer(Modifier.height(20.dp))

            Text("当前的状态：${transition.currentState.alias}")

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = { currentState.targetState = BoxState.Small }) {
                    Text("小")
                }
                Button(onClick = { currentState.targetState = BoxState.Medium }) {
                    Text("中")
                }
                Button(onClick = { currentState.targetState = BoxState.Big }) {
                    Text("大")
                }
            }


        }


    }

    class TransitionData(
        size: State<Dp>,
        color: State<Color>,
    ) {

        val size by size
        val color by color

    }

    @Composable
    fun changeBoxSizeAndColor(big: Boolean): TransitionData {

        val transition = updateTransition(targetState = big, label = "")
        val size = transition.animateDp(label = "") {
            when (it) {
                true -> 200.dp
                false -> 50.dp
            }
        }

        val color = transition.animateColor(label = "") {
            when (it) {
                true -> Color.Red.copy(0.2f)
                false -> Color.Blue.copy(0.2f)
            }
        }

        return TransitionData(
            size = size,
            color = color
        )

    }

    @Composable
    private fun TransitionExample3(
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var bigSize by remember { mutableStateOf(false) }
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f), contentAlignment = Alignment.Center
            ) {
                val animationData = changeBoxSizeAndColor(bigSize)
                Box(
                    Modifier
                        .size(animationData.size)
                        .background(animationData.color)
                )
            }
            Button(onClick = { bigSize=!bigSize }) {
                Text("切换")
            }
        }
    }

}
