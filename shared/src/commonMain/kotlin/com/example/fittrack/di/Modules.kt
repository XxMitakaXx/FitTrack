package com.example.fittrack.di

import com.example.fittrack.app.data.authentication.RemoteAuthentication
import com.example.fittrack.app.data.jwt.JWTUtil
import com.example.fittrack.app.data.jwt.RemoteJWTDataSource
import com.example.fittrack.app.domain.authentication.AuthenticationDataSource
import com.example.fittrack.app.domain.jwt.JWTDataSource
import com.example.fittrack.app.presentation.login.LoginViewModel
import com.example.fittrack.app.presentation.register.RegisterViewModel
import com.example.fittrack.training.presentation.home.TrainingViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    singleOf(::RemoteAuthentication).bind<AuthenticationDataSource>()
    singleOf(::RemoteJWTDataSource).bind<JWTDataSource>()
    singleOf(::JWTUtil)

    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::TrainingViewModel)
}