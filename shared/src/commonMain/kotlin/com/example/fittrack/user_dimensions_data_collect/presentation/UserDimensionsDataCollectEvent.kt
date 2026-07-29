package com.example.fittrack.user_dimensions_data_collect.presentation

sealed interface UserDimensionsDataCollectEvent {
    data object OnSavedUserDimensionsDataSuccess: UserDimensionsDataCollectEvent
}