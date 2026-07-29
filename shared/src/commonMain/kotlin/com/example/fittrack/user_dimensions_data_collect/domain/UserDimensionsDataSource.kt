package com.example.fittrack.user_dimensions_data_collect.domain

import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.user_dimensions_data_collect.domain.models.UserDimensionsDataDTO
import com.example.fittrack.core.data.util.Result

interface UserDimensionsDataSource {
    suspend fun saveUserDimensionsData(userDimensionsDataDTO: UserDimensionsDataDTO): Result<Unit, NetworkError>
}