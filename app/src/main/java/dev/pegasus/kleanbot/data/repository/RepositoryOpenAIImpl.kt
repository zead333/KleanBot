package dev.pegasus.kleanbot.data.repository

import dev.pegasus.kleanbot.data.dataSources.DataSourceRemoteOpenAI
import dev.pegasus.kleanbot.data.entities.OpenAIRequest
import dev.pegasus.kleanbot.data.entities.OpenAIResponse
import dev.pegasus.kleanbot.domain.repository.RepositoryOpenAI
import dev.pegasus.kleanbot.presentation.uiStates.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class RepositoryOpenAIImpl(private val dataSource: DataSourceRemoteOpenAI) : RepositoryOpenAI {

    override suspend fun getChatCompletion(openAIRequest: OpenAIRequest): Flow<ApiResponse<OpenAIResponse>> = withContext(Dispatchers.IO) {
        dataSource.getChatCompletion(openAIRequest)
    }
}