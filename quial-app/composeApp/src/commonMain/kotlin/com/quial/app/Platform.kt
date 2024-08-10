package com.quial.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform