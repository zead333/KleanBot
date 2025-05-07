package dev.pegasus.kleanbot.presentation.enums

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

enum class OpenAIRole(val value: String) {
    CHAT_GPT("chatgpt"),
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system");

    companion object {
        const val ALL = "All"

        fun fromValue(value: String): OpenAIRole? {
            return OpenAIRole.entries.find { it.value == value }
        }
    }
}