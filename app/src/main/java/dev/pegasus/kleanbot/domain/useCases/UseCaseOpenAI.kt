package dev.pegasus.kleanbot.domain.useCases

import android.util.Log
import dev.pegasus.kleanbot.R
import dev.pegasus.kleanbot.data.entities.Message
import dev.pegasus.kleanbot.data.entities.OpenAIRequest
import dev.pegasus.kleanbot.data.entities.OpenAIResponse
import dev.pegasus.kleanbot.data.repository.RepositoryOpenAIImpl
import dev.pegasus.kleanbot.presentation.enums.OpenAIRole
import dev.pegasus.kleanbot.presentation.uiStates.ApiResponse
import dev.pegasus.kleanbot.utilities.ConstantUtils
import dev.pegasus.kleanbot.utilities.ConstantUtils.TAG
import dev.pegasus.kleanbot.utilities.InternetManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class UseCaseOpenAI(
    private val repositoryOpenAIImpl: RepositoryOpenAIImpl,
    private val internetManager: InternetManager,
) {

    private val _chatMessageList = ArrayList<Message>()
    private val chatMessageList: List<Message> get() = _chatMessageList.toList()

    fun getChatResponse(queryText: String?): Flow<ApiResponse<List<Message>>> = flow {
        Log.d(TAG, "UseCaseAILanguageLearningAssistant: getChatResponse: query: $queryText")

        val query = queryText?.trim().orEmpty()
        if (query.isEmpty()) {
            emit(ApiResponse.Failure(R.string.toast_please_enter_text_before_searching))
            return@flow
        }

        if (!internetManager.isInternetConnected) {
            emit(ApiResponse.Failure(R.string.toast_no_internet_connection))
            return@flow
        }

        val systemMessage = Message(role = OpenAIRole.SYSTEM.value, content = "Reply all chats only in `en` language code")
        val userMessage = Message(role = OpenAIRole.USER.value, content = query)

        _chatMessageList += listOf(systemMessage, userMessage)

        val updateList = chatMessageList.filter { it.role != OpenAIRole.SYSTEM.value }
        emit(ApiResponse.Success(updateList))
        emit(ApiResponse.Loading)

        val openAIRequest = OpenAIRequest(
            model = ConstantUtils.OPEN_AI_MODEL,
            messages = chatMessageList,
            temperature = ConstantUtils.OPEN_AI_TEMPERATURE,
            max_completion_tokens = ConstantUtils.OPEN_AI_MAX_COMPLETION_TOKEN
        )

        repositoryOpenAIImpl.getChatCompletion(openAIRequest)
            .map { response ->
                when (response) {
                    is ApiResponse.Success -> onApiSuccessResponse(response)
                    is ApiResponse.Failure -> response
                    is ApiResponse.Loading -> ApiResponse.Loading
                }
            }
            .flowOn(Dispatchers.Default)
            .collect { emit(it) }
    }.catch { exception ->
        Log.e(TAG, "UseCaseAILanguageLearningAssistant: getChatResponse: Exception:", exception)
        emit(ApiResponse.Failure(R.string.toast_something_went_wrong))
    }

    private fun onApiSuccessResponse(response: ApiResponse.Success<OpenAIResponse>): ApiResponse<List<Message>> {
        return response.data.choices.getOrNull(0)?.let {
            val assistantReply = it.message.content
            if (assistantReply.isEmpty()) {
                return ApiResponse.Failure(R.string.toast_no_result_found)
            }

            val assistantMessage = Message(OpenAIRole.ASSISTANT.value, assistantReply)
            _chatMessageList.add(assistantMessage)

            val updateList = chatMessageList.filter { it.role != OpenAIRole.SYSTEM.value }
            ApiResponse.Success(updateList)
        } ?: run {
            ApiResponse.Failure(R.string.toast_no_result_found)
        }
    }
}