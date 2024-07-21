package com.kmp.template.android.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.discoveracity.android.ui.app.navigation.Screen
import com.kmp.template.android.viewmodel.MyViewModel
import java.util.UUID

@Composable
fun HomeScreen(
    myViewModel: MyViewModel,
    navController: NavHostController
) {

    val myDomainObject by myViewModel.myDomainObject.collectAsState()
    val isInProgress by myViewModel.isLoading.collectAsState()

    val context = LocalContext.current

    if (myDomainObject!= null && !isInProgress) {
        LaunchedEffect(key1 = UUID.randomUUID().toString()) {
            Toast.makeText(context, "MyDomainObject loaded", Toast.LENGTH_SHORT).show()
        }
    }

    val buttonText: String = if (isInProgress) {
        "Loading"
    } else {
        "Click to load"
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Button(onClick = { myViewModel.invokeMyUseCase() }, enabled = !isInProgress) {
            Text(text = buttonText)
        }

        Text(text = if (isInProgress) "" else "MyDomainObject value: ${myDomainObject?.myText ?: ""} ")

        if (myDomainObject != null && !isInProgress) {
            Button(onClick = { navController.navigate(Screen.MyScreen.createRoute(myDomainObject!!)) }) {
                Text(text = "My Domain Object Details")
            }
        }
    }
}