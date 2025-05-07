package dev.pegasus.kleanbot.presentation.uiStates

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

sealed class ApiResponse<out T> {
    object Loading : ApiResponse<Nothing>()
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Failure(val messageResId: Int) : ApiResponse<Nothing>()
}