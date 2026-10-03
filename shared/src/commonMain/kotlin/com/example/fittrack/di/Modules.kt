package com.example.fittrack.di

import com.example.fittrack.add_exercise.data.RemoteAddExerciseDataSource
import com.example.fittrack.add_exercise.domain.AddExerciseDataSource
import com.example.fittrack.add_exercise.presentation.AddExerciseViewModel
import com.example.fittrack.app.data.authentication.RemoteAuthentication
import com.example.fittrack.app.data.jwt.JWTUtil
import com.example.fittrack.app.data.jwt.RemoteJWTDataSource
import com.example.fittrack.app.domain.authentication.AuthenticationDataSource
import com.example.fittrack.app.domain.jwt.JWTDataSource
import com.example.fittrack.app.presentation.login.LoginViewModel
import com.example.fittrack.app.presentation.register.RegisterViewModel
import com.example.fittrack.app.presentation.util.AppLogger
import com.example.fittrack.app.presentation.util.KermitAppLogger
import com.example.fittrack.body_weight.presentation.BodyWeightViewModel
import com.example.fittrack.programs.data.RemoteProgramDataSource
import com.example.fittrack.training.data.RemoteUserTrainingDataSource
import com.example.fittrack.programs.domain.ProgramDataSource
import com.example.fittrack.programs.presentation.ProgramsViewModel
import com.example.fittrack.training.domain.UserTrainingDataSource
import com.example.fittrack.training.presentation.TrainingViewModel
import com.example.fittrack.user_dimensions_data_collect.data.RemoteUserDimensionsDataSource
import com.example.fittrack.user_dimensions_data_collect.domain.UserDimensionsDataSource
import com.example.fittrack.user_dimensions_data_collect.presentation.UserDimensionsDataCollectViewModel
import com.example.fittrack.welcome_page.presentation.WelcomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    singleOf(::RemoteAuthentication).bind<AuthenticationDataSource>()
    singleOf(::RemoteJWTDataSource).bind<JWTDataSource>()
    singleOf(::RemoteProgramDataSource).bind<ProgramDataSource>()
    singleOf(::RemoteUserTrainingDataSource).bind<UserTrainingDataSource>()
    singleOf(::RemoteUserDimensionsDataSource).bind<UserDimensionsDataSource>()
    singleOf(::RemoteAddExerciseDataSource).bind<AddExerciseDataSource>()
    singleOf(::KermitAppLogger).bind<AppLogger>()

    singleOf(::JWTUtil)

    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::TrainingViewModel)
    viewModelOf(::ProgramsViewModel)
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::UserDimensionsDataCollectViewModel)
    viewModelOf(::BodyWeightViewModel)
    viewModelOf(::AddExerciseViewModel)
}