package com.example.fittrack.core.data

import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(error = NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(error = NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        return Result.Error(error = NetworkError.UNKNOWN)
    }

    return responseToResult(response = response)
}