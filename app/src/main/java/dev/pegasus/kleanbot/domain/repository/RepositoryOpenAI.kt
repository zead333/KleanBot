package dev.pegasus.kleanbot.domain.repository

import dev.pegasus.kleanbot.data.entities.OpenAIRequest
import dev.pegasus.kleanbot.data.entities.OpenAIResponse
import dev.pegasus.kleanbot.presentation.uiStates.ApiResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

interface RepositoryOpenAI {
    suspend fun getChatCompletion(openAIRequest: OpenAIRequest): Flow<ApiResponse<OpenAIResponse>>
}