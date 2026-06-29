package com.example.fittrack.di

import com.example.fittrack.core.data.createHttpClient
import com.example.fittrack.user.domain.SecurityStorageManager
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformModule = module {
    single { createHttpClient(engine = Darwin.create()) }
    single { SecurityStorageManager() }
}