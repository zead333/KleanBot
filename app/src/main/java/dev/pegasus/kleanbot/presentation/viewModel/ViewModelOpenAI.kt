package dev.pegasus.kleanbot.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pegasus.kleanbot.data.entities.Message
import dev.pegasus.kleanbot.domain.useCases.UseCaseOpenAI
import dev.pegasus.kleanbot.presentation.uiStates.ApiResponse
import dev.pegasus.kleanbot.utilities.SingleLiveEvent
import kotlinx.coroutines.launch

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class ViewModelOpenAI(private val useCaseOpenAI: UseCaseOpenAI) : ViewModel() {

    private val _chatResponseLiveData = MutableLiveData<ApiResponse<List<Message>>>()
    val chatResponseLiveData: LiveData<ApiResponse<List<Message>>> get() = _chatResponseLiveData

    private val _queryLiveData = SingleLiveEvent<String>()
    val queryLiveData: LiveData<String> get() = _queryLiveData

    fun fetchChatResponse(query: String?) = viewModelScope.launch {
        useCaseOpenAI
            .getChatResponse(query)
            .collect { response ->
                _chatResponseLiveData.value = response

                if (response is ApiResponse.Failure) {
                    query?.let { _queryLiveData.value = it }
                }
            }
    }
}