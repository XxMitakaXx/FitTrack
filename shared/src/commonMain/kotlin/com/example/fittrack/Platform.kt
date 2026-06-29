package com.example.fittrack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform