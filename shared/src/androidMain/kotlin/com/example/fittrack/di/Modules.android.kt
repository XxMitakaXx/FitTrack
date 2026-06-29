package com.example.fittrack.di

import com.example.fittrack.core.data.createHttpClient
import com.example.fittrack.user.domain.SecurityStorageManager
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    single { createHttpClient(OkHttp.create()) }
    singleOf(::SecurityStorageManager)
}