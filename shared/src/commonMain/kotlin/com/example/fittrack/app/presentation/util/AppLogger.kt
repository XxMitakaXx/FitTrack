package com.example.fittrack.app.presentation.util

interface AppLogger {
    fun i(message: String, tag: String? = null, throwable: Throwable? = null)
    fun e(message: String, tag: String? = null, throwable: Throwable? = null)
    fun w(message: String, tag: String? = null, throwable: Throwable? = null)
    fun d(message: String, tag: String? = null, throwable: Throwable? = null)
    fun v(message: String, tag: String? = null, throwable: Throwable? = null)
}