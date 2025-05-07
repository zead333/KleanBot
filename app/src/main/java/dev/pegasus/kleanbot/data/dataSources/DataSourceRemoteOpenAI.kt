package dev.pegasus.kleanbot.data.dataSources

import android.util.Log
import dev.pegasus.kleanbot.R
import dev.pegasus.kleanbot.data.dataSources.retrofit.api.ApiServiceOpenAI
import dev.pegasus.kleanbot.data.entities.OpenAIRequest
import dev.pegasus.kleanbot.data.entities.OpenAIResponse
import dev.pegasus.kleanbot.presentation.uiStates.ApiResponse
import dev.pegasus.kleanbot.utilities.ConstantUtils.TAG
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class DataSourceRemoteOpenAI(private val apiServiceOpenAI: ApiServiceOpenAI) {

    fun getChatCompletion(openAIRequest: OpenAIRequest): Flow<ApiResponse<OpenAIResponse>> = callbackFlow {
        try {
            val response = apiServiceOpenAI.getChatCompletion(openAIRequest) // Pass the correct request object
            val body = response.body()
            Log.d(TAG, "DataSourceRemoteOpenAI: getChatCompletion: Response: isSuccessful: ${response.isSuccessful}, body: $body")

            if (response.isSuccessful) {
                when (body == null) {
                    true -> trySend(ApiResponse.Failure(R.string.toast_no_result_found))
                    false -> trySend(ApiResponse.Success(body))
                }
            } else {
                Log.e(TAG, "DataSourceRemoteOpenAI: getChatCompletion: ErrorBody: ${response.errorBody()?.string()}")
                trySend(ApiResponse.Failure(R.string.toast_something_went_wrong))
            }
        } catch (ex: Exception) {
            Log.e(TAG, "DataSourceRemoteOpenAI: getChatCompletion: Exception: ", ex)
            trySend(ApiResponse.Failure(R.string.toast_something_went_wrong))
        }
        awaitClose {}
    }
}