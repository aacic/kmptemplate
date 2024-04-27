package com.kmp.template.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.discoveracity.android.ui.app.navigation.Screen
import com.kmp.template.domain.MyDomainObject

@Composable
fun HomeScreen(navController: NavHostController, myDomainObject: MyDomainObject?) {
    myDomainObject?.let{
        Column(modifier = Modifier.padding(8.dp)) {
            Button(onClick = { navController.navigate(Screen.MyScreen.createRoute(it)) }) {
                Text(text = "MyDomainObject: ${it.myText}")
            }
        }
    }
}