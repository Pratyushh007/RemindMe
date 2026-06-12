package com.pratyush.remainderapp.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pratyush.remainderapp.ui.screens.HomeScreen
import com.pratyush.remainderapp.ui.screens.IntroScreen
import com.pratyush.remainderapp.ui.screens.LoginScreen

sealed class Screen(val route: String) {
    object Intro : Screen("intro")
    object Login : Screen("login")
    object Home : Screen("home")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Intro.route,
        enterTransition = {
            fadeIn(animationSpec = tween(400)) +
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(400)) +
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(400)) +
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(400)) +
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
        }
    ) {
        composable(
            route = Screen.Intro.route,
            enterTransition = { fadeIn(tween(0)) },
            exitTransition = { fadeOut(tween(300)) }
        ) {
            IntroScreen(
                onIntroFinished = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Intro.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen()
        }
    }
}
