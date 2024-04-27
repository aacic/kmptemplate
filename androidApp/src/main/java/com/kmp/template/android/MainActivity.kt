package com.kmp.template.android

import BottomNavigationBar
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.discoveracity.android.ui.app.navigation.Screen
import com.kmp.template.android.ui.HomeScreen
import com.kmp.template.android.ui.MyScreen
import com.kmp.template.android.ui.ProfileScreen
import com.kmp.template.android.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(myViewModel)
                }
            }
        }
    }
}
@Composable
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter"
)
fun AppNavigation(myViewModel: MyViewModel) {
    val myDomainObject = myViewModel.myDomainObject.collectAsState().value
    val navController = rememberNavController()
    val currentRoute = getCurrentRoute(navController)

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.MyScreen.route) {
                BottomNavigationBar(navController)
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
            composable(Screen.HomeScreen.route) { HomeScreen(navController, myDomainObject) }
            composable(Screen.ProfileScreen.route) { ProfileScreen() }

            composable(
                route = Screen.MyScreen.route,
                arguments = listOf(navArgument("myDomainObjectText") { type = NavType.StringType })
            ) { backStackEntry ->
                MyScreen(backStackEntry)
            }
        }
    }
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
