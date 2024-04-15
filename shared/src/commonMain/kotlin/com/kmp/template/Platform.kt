package com.kmp.template

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform