package com.example.fittrack.user_dimensions_data_collect.presentation

sealed interface UserDimensionsDataCollectAction {
    data class OnHeightValueChange(val height: String): UserDimensionsDataCollectAction
    data class OnAgeValueChange(val age: String): UserDimensionsDataCollectAction
    data class OnGenderValueChange(val gender: String): UserDimensionsDataCollectAction
    data object OnSave: UserDimensionsDataCollectAction
}