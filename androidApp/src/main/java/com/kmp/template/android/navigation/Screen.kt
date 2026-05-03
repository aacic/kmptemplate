package com.kmp.template.android.navigation

import androidx.annotation.DrawableRes
import com.kmp.template.android.R
import com.kmp.template.domain.MyDomainObject

sealed class Screen(val route: String, @DrawableRes val icon: Int, val title: String) {
    object HomeScreen : Screen("home", R.drawable.icon, "Home")
    object ProfileScreen : Screen("profile", R.drawable.icon, "Profile")
    object MyScreen : Screen("myscreen?myDomainObjectText={myDomainObjectText}", R.drawable.icon, "MyScreen")   {
        fun createRoute(myDomainObject: MyDomainObject): String {
            return "myscreen?myDomainObjectText=${myDomainObject.myText}"
        }
    }
}