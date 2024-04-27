package com.kmp.template.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry

@Composable
fun MyScreen(backStackEntry: NavBackStackEntry) {
    val myDomainObjectText = backStackEntry.arguments?.getString("myDomainObjectText")
    myDomainObjectText?.let {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "MyDomainObjectText: $myDomainObjectText")
        }
    }


}