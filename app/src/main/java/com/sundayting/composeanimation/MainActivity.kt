package com.sundayting.composeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sundayting.composeanimation.ui.main.MainPage
import com.sundayting.composeanimation.ui.value_base.AnimatablePage
import com.sundayting.composeanimation.ui.value_base.HighLevelPage
import com.sundayting.composeanimation.ui.value_base.TransitionPage
import com.sundayting.composeanimation.ui.value_base.ValueBasePage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MaterialTheme{
                val navHostController = rememberNavController()
                CompositionLocalProvider(
                    LocalTextStyle provides MaterialTheme.typography.bodyLarge
                ) {
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navHostController,
                        startDestination = MainPage.ROUTE,
                        enterTransition = {
                            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                        }
                    ) {

                        composable(
                            MainPage.ROUTE
                        ) {
                            MainPage.Screen(
                                navHostController = navHostController
                            )
                        }

                        composable(
                            ValueBasePage.ROUTE
                        ) {
                            ValueBasePage.Screen(
                                navHostController = navHostController
                            )
                        }

                        composable(
                            AnimatablePage.ROUTE
                        ){
                            AnimatablePage.Screen(
                                navHostController = navHostController
                            )
                        }

                        composable(
                            TransitionPage.ROUTE
                        ){
                            TransitionPage.Screen(
                                navHostController = navHostController
                            )
                        }

                        composable(
                            HighLevelPage.ROUTE
                        ){
                            HighLevelPage.Screen(
                                navHostController = navHostController
                            )
                        }

                    }
                }

            }
        }
    }
}

